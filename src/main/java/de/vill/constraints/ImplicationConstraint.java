package de.vill.constraints;

import de.vill.model.building.VariableReference;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.pbc.PBCLiteralConstraint;
import de.vill.encoding.SubstitutionVariableIndex;
import org.prop4j.Implies;
import org.prop4j.Node;

import java.util.Map;

public class ImplicationConstraint extends de.vill.model.constraint.ImplicationConstraint implements IPbcEncodable {
    public ImplicationConstraint(Constraint left, Constraint right) {
        super(left, right);
    }

    @Override
    public Node getNode() {
        var node = new Implies(((IPbcEncodable)getLeft()).getNode(), ((IPbcEncodable)getRight()).getNode());
        return node;
    }

    @Override
    public PBCLiteralConstraint extractTseitinSubConstraints(Map<Integer, Constraint> substitutionMapping) {
        Constraint leftSub = ((IPbcEncodable)getLeft()).extractTseitinSubConstraints(substitutionMapping);
        Constraint rightSub = ((IPbcEncodable)getRight()).extractTseitinSubConstraints(substitutionMapping);
        int substitutionIndex = SubstitutionVariableIndex.getInstance().getIndex();
        substitutionMapping.put(substitutionIndex, new de.vill.model.constraint.ImplicationConstraint(leftSub, rightSub));

        return new PBCLiteralConstraint(
                new LiteralConstraint(new VariableReference() {
                    @Override
                    public String getIdentifier() {
                        return "x_" + substitutionIndex;
                    }
                })
        );
    }
}
