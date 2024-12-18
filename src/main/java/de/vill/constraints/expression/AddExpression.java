package de.vill.constraints.expression;

import de.vill.model.expression.Expression;
import de.vill.pbc.Literal;
import de.vill.pbc.PBConstraint;

import java.util.LinkedList;
import java.util.List;

public class AddExpression extends de.vill.model.expression.AddExpression implements IExpressionOpbEncodeable {
    public AddExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public List<Literal> getAsSum(List<PBConstraint> additionalSubstitution) {
        List<Literal> result = new LinkedList<>();
        result.addAll(((IExpressionOpbEncodeable)getLeft()).getAsSum(additionalSubstitution));
        List<Literal> rightSum = ((IExpressionOpbEncodeable)getRight()).getAsSum(additionalSubstitution);
        result.addAll(rightSum);
        return result;
    }
}
