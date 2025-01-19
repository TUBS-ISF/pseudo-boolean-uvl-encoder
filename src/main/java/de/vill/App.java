package de.vill;

import de.vill.main.UVLModelFactory;
import de.vill.model.FeatureModel;
import de.vill.model.constraint.Constraint;
import de.vill.util.ConvertFeatureCardinalityForOPB;
import de.vill.util.FeatureModelEncoding;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        final File UVL_FILE = new File("/home/stefan/stefan-vill-master/eval/tmp_models/root_8.uvl");
        final File TARGET_FILE = new File("/home/stefan/test/test.opb");
        String content = new String(Files.readAllBytes(UVL_FILE.toPath()));
        UVLModelFactory uvlModelFactory = new UVLModelFactory();
        FeatureModel featureModel = uvlModelFactory.parse(content);

        ConvertFeatureCardinalityForOPB convertFeatureCardinalityForOPB = new ConvertFeatureCardinalityForOPB();
        convertFeatureCardinalityForOPB.convertFeatureModel(featureModel);

        List<Constraint> constraintList = new LinkedList<>();
        for (Constraint constraint : featureModel.getOwnConstraints()){
            constraintList.add(de.vill.util.ReplaceClasses.replace(constraint));
        }
        featureModel.getOwnConstraints().clear();
        featureModel.getOwnConstraints().addAll(constraintList);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TARGET_FILE))) {
            FeatureModelEncoding.writeOPBStringToFile(featureModel, UVL_FILE, TARGET_FILE, writer);

            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }



    }
}
