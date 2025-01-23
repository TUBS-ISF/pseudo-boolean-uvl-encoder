package de.vill.encoding;

import de.vill.model.Feature;
import de.vill.model.Group;
import de.vill.pbc.OPBResult;

import java.util.List;

public class FeatureEncoding {
    public static void toOPBString(Feature feature, OPBResult result) {
        List<Group> childGroups = feature.getChildren();
        for (Group group : childGroups){
            switch (group.GROUPTYPE) {
                case OPTIONAL:
                    result.opbString.append(group.getFeatures().size());
                    result.opbString.append(" * ");
                    result.opbString.append(feature.getQuotedFeatureName());
                    for (Feature child : group.getFeatures()){
                        result.opbString.append(" -1 * ");
                        result.opbString.append(child.getQuotedFeatureName());
                    }
                    result.opbString.append(" >= 0;\n");
                    result.numberConstraints++;
                    break;
                case MANDATORY:
                    result.opbString.append(group.getFeatures().size());
                    result.opbString.append(" * ");
                    result.opbString.append(feature.getQuotedFeatureName());
                    for (Feature child : group.getFeatures()){
                        result.opbString.append(" -1 * ");
                        result.opbString.append(child.getQuotedFeatureName());
                    }
                    result.opbString.append(" = 0;\n");
                    result.numberConstraints += 2;
                    break;
                case OR:
                    result.opbString.append("-1 * ");
                    result.opbString.append(feature.getQuotedFeatureName());
                    for (Feature child : group.getFeatures()){
                        result.opbString.append(" + ");
                        result.opbString.append(child.getQuotedFeatureName());
                    }
                    result.opbString.append(" >= 0;\n");
                    result.numberConstraints++;
                    result.opbString.append(group.getFeatures().size());
                    result.opbString.append(" * ");
                    result.opbString.append(feature.getQuotedFeatureName());
                    for (Feature child : group.getFeatures()){
                        result.opbString.append(" -1 * ");
                        result.opbString.append(child.getQuotedFeatureName());
                    }
                    result.opbString.append(" >= 0;\n");
                    result.numberConstraints++;
                    break;
                case ALTERNATIVE:
                    result.opbString.append(feature.getQuotedFeatureName());
                    for (Feature child : group.getFeatures()){
                        result.opbString.append(" -1 * ");
                        result.opbString.append(child.getQuotedFeatureName());
                    }
                    result.opbString.append(" = 0;\n");
                    result.numberConstraints += 2;
                    break;
                case GROUP_CARDINALITY:
                    result.opbString.append(group.getFeatures().size());
                    result.opbString.append(" * ");
                    result.opbString.append(feature.getQuotedFeatureName());
                    for (Feature child : group.getFeatures()){
                        result.opbString.append(" -1 * ");
                        result.opbString.append(child.getQuotedFeatureName());
                    }
                    result.opbString.append(" >= 0;\n");
                    result.numberConstraints++;


                    result.opbString.append("-");
                    result.opbString.append(group.getCardinality().lower);
                    result.opbString.append(" * ");
                    result.opbString.append(feature.getQuotedFeatureName());
                    for (Feature child : group.getFeatures()){
                        result.opbString.append(" +1 * ");
                        result.opbString.append(child.getQuotedFeatureName());
                    }
                    result.opbString.append(" >= ");
                    result.opbString.append(0);
                    result.opbString.append(";\n");


                    result.numberConstraints++;
                    result.opbString.append(feature.getQuotedFeatureName());
                    for (Feature child : group.getFeatures()){
                        result.opbString.append(" +1 * ");
                        result.opbString.append(child.getQuotedFeatureName());
                    }
                    result.opbString.append(" <= ");
                    result.opbString.append(group.getCardinality().upper + 1);
                    result.opbString.append(";\n");
            }
            for (Feature child : group.getFeatures()){
                result.numberVariables++;
                if(child.getChildren().size() > 0) {
                    toOPBString(child, result);
                }
            }
        }
    }
}
