package de.vill.constraints.expression;

import de.vill.model.expression.Expression;
import de.vill.pbc.Literal;
import de.vill.pbc.PBConstraint;

import java.util.List;

public class ParenthesisExpression extends de.vill.model.expression.ParenthesisExpression implements IExpressionOpbEncodeable {
    public ParenthesisExpression(Expression content) {
        super(content);
    }

    @Override
    public List<Literal> getAsSum(List<PBConstraint> additionalSubstitution) {
        return ((IExpressionOpbEncodeable)getContent()).getAsSum(additionalSubstitution);
    }
}
