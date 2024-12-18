package de.vill.constraints;

import de.vill.model.building.VariableReference;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.pbc.PBCLiteralConstraint;
import de.vill.util.SubstitutionVariableIndex;
import org.prop4j.Node;
import org.prop4j.Or;

import java.util.Map;

import static de.vill.util.Utility.getMaxOrConstraint;

public class OrConstraint extends de.vill.model.constraint.OrConstraint implements IPbcEncodable {
    public OrConstraint(Constraint left, Constraint right) {
        super(left, right);
    }

    @Override
    public Node getNode() {
        var node = new Or();
        node.setChildren(((IPbcEncodable)getLeft()).getNode(), ((IPbcEncodable)getRight()).getNode());
        return node;
    }

    @Override
    public PBCLiteralConstraint extractTseitinSubConstraints(Map<Integer, Constraint> substitutionMapping) {
        Constraint leftSub = getMaxOrConstraint(getLeft(), substitutionMapping);
        Constraint rightSub = getMaxOrConstraint(getRight(), substitutionMapping);
        int substitutionIndex = SubstitutionVariableIndex.getInstance().getIndex();
        substitutionMapping.put(substitutionIndex, new de.vill.model.constraint.OrConstraint(leftSub, rightSub));

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
