package de.vill.constraints.expression;

import de.vill.model.expression.Expression;
import de.vill.pbc.Literal;
import de.vill.pbc.PBConstraint;

import java.util.List;

public class SubExpression extends de.vill.model.expression.SubExpression implements IExpressionOpbEncodeable {
    public SubExpression(Expression left, Expression right) {
        super(left, right);
    }

    public List<Literal> getAsSum(List<PBConstraint> additionalSubstitution) {
        var result = ((IExpressionOpbEncodeable)getLeft()).getAsSum(additionalSubstitution);
        var rightResult = ((IExpressionOpbEncodeable)getRight()).getAsSum(additionalSubstitution);
        for (Literal l : rightResult){
            l.factor *= -1;
        }
        result.addAll(rightResult);
        return result;
    }
}
