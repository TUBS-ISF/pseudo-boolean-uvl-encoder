package de.vill.encoding;

import de.vill.constraints.IPbcEncodable;
import de.vill.model.FeatureModel;
import de.vill.model.constraint.Constraint;
import de.vill.pbc.OPBResult;
import org.prop4j.And;
import org.prop4j.Literal;
import org.prop4j.Node;
import org.prop4j.Or;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import static de.vill.encoding.Utility.encodeConstraintTseitinStyle;
import static de.vill.encoding.Utility.substituteExpressions;

public class FeatureModelEncoding {
    public static StringBuilder toOPBString(FeatureModel featureModel){
        OPBResult result = new OPBResult();
        result.numberVariables++;
        result.opbString.append(featureModel.getRootFeature().getQuotedFeatureName());
        result.opbString.append(" >= 1;\n");
        result.numberConstraints++;
        FeatureEncoding.toOPBString(featureModel.getRootFeature(), result);

        List<Constraint> constraints = featureModel.getConstraints();
        constraints.addAll(featureModel.getFeatureConstraints());

        for(Constraint constraint : constraints){
            //constraintDistributiveToOPB(constraint,result);
            encodeConstraintTseitinStyle(constraint, result);
        }

        SubstitutionVariableIndex substitutionVariableIndex = SubstitutionVariableIndex.getInstance();
        result.numberVariables += substitutionVariableIndex.peekIndex();
        String header = "#variable= " + result.numberVariables + " #constraint= " + result.numberConstraints + "\n";
        result.opbString.insert(0,header);

        return result.opbString;
    }

    public static void writeOPBStringToFile(FeatureModel featureModel, File fmFile, File opbFile, Writer writer) throws IOException {
        OPBResult result = new OPBResult();
        result.numberVariables++;
        result.opbString.append(featureModel.getRootFeature().getQuotedFeatureName());
        result.opbString.append(" >= 1;\n");
        result.numberConstraints++;
        FeatureEncoding.toOPBString(featureModel.getRootFeature(), result);

        SubstitutionVariableIndex substitutionVariableIndex = SubstitutionVariableIndex.getInstance();
        result.numberVariables += substitutionVariableIndex.peekIndex();

        List<Constraint> constraints = featureModel.getConstraints();
        constraints.addAll(featureModel.getFeatureConstraints());

        for (Constraint constraint : constraints){
            var current_sub_index_before = substitutionVariableIndex.peekIndex();
            var cnfConstraint = ((IPbcEncodable)substituteExpressions(constraint, result)).getNode().toCNF();
            var current_sub_index_after = substitutionVariableIndex.peekIndex();
            result.numberVariables += current_sub_index_after - current_sub_index_before;
            List<Node> clauses = new LinkedList<>();
            if (cnfConstraint instanceof And) {
                for(Node andChild : cnfConstraint.getChildren()){
                    if (!(andChild instanceof Or)){
                        clauses.add(new Or(andChild));
                    }else{
                        clauses.add(andChild);
                    }
                }
            }else{
                if (!(cnfConstraint instanceof Or)){
                    clauses.add(new Or(cnfConstraint));
                }else{
                    clauses.add(cnfConstraint);
                }
            }
            for (Node clause : clauses){
                int negationCount = 0;
                for (Literal literal : clause.getLiterals()) {
                    String variableName = (String)literal.var;
                    if (literal.positive){
                        result.opbString.append(" +1 * ");
                    }else{
                        result.opbString.append(" -1 * ");
                        negationCount++;
                    }

                    result.opbString.append("\"");
                    result.opbString.append(variableName);
                    result.opbString.append("\"");
                }
                result.opbString.append(" >= ");
                result.opbString.append(String.valueOf(1 - negationCount));
                result.opbString.append(";\n");
            }
        }

        String header = "#variable= " + result.numberVariables + " #constraint= " + result.numberConstraints + "\n";
        result.opbString.insert(0,header);

        writer.append(result.opbString);


    }
}
