package de.vill.constraints;

import de.vill.model.building.VariableReference;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.expression.Expression;
import de.vill.pbc.PBCLiteralConstraint;
import de.vill.encoding.SubstitutionVariableIndex;
import org.prop4j.Node;

import java.util.Map;

public class EqualsEquationConstraint extends de.vill.model.constraint.EqualEquationConstraint implements IPbcEncodable{
    public EqualsEquationConstraint(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Node getNode() {
        return null;
    }

    @Override
    public PBCLiteralConstraint extractTseitinSubConstraints(Map<Integer, Constraint> substitutionMapping) {
        int substitutionIndex = SubstitutionVariableIndex.getInstance().getIndex();
        substitutionMapping.put(substitutionIndex, this);
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
