package de.vill;

import de.vill.main.UVLModelFactory;
import de.vill.model.FeatureModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {

            Path filePath = Paths.get("");
            String content = new String(Files.readAllBytes(filePath));
            UVLModelFactory uvlModelFactory = new UVLModelFactory();
            FeatureModel featureModel = uvlModelFactory.parse(content);


    }
}
