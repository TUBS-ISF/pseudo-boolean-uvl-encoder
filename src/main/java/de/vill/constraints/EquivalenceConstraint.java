package de.vill.constraints;

import de.vill.model.building.VariableReference;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.pbc.PBCLiteralConstraint;
import de.vill.util.SubstitutionVariableIndex;
import org.prop4j.And;
import org.prop4j.Implies;
import org.prop4j.Node;

import java.util.Map;

public class EquivalenceConstraint extends de.vill.model.constraint.EquivalenceConstraint implements IPbcEncodable {
    public EquivalenceConstraint(Constraint left, Constraint right) {
        super(left, right);
    }

    @Override
    public Node getNode() {
        var node = new And(
                new Implies(((IPbcEncodable)getLeft()).getNode(), ((IPbcEncodable)getRight()).getNode()),
                new Implies(((IPbcEncodable)getRight()).getNode(), ((IPbcEncodable)getLeft()).getNode())
        );
        return node;
    }

    @Override
    public PBCLiteralConstraint extractTseitinSubConstraints(Map<Integer, Constraint> substitutionMapping) {

        Constraint leftSub = ((IPbcEncodable)getLeft()).extractTseitinSubConstraints(substitutionMapping);
        Constraint rightSub = ((IPbcEncodable)getRight()).extractTseitinSubConstraints(substitutionMapping);
        int substitutionIndex = SubstitutionVariableIndex.getInstance().getIndex();
        substitutionMapping.put(substitutionIndex, new de.vill.model.constraint.EquivalenceConstraint(leftSub, rightSub));

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
