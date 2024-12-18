package de.vill.constraints;

import de.vill.model.building.VariableReference;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.pbc.PBCLiteralConstraint;
import de.vill.util.SubstitutionVariableIndex;
import org.prop4j.And;
import org.prop4j.Node;

import java.util.Map;

import static de.vill.util.Utility.getMaxAndConstraint;

public class AndConstraint extends de.vill.model.constraint.AndConstraint implements IPbcEncodable {
    public AndConstraint(Constraint left, Constraint right) {
        super(left, right);
    }

    @Override
    public Node getNode() {
        var node = new And();
        node.setChildren(((IPbcEncodable)getLeft()).getNode(), ((IPbcEncodable)getRight()).getNode());
        return node;
    }

    @Override
    public PBCLiteralConstraint extractTseitinSubConstraints(Map<Integer, Constraint> substitutionMapping) {
        Constraint leftSub = getMaxAndConstraint(getLeft(), substitutionMapping);
        Constraint rightSub = getMaxAndConstraint(getRight(), substitutionMapping);
        int substitutionIndex = SubstitutionVariableIndex.getInstance().getIndex();
        substitutionMapping.put(substitutionIndex, new de.vill.model.constraint.AndConstraint(leftSub, rightSub));

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
