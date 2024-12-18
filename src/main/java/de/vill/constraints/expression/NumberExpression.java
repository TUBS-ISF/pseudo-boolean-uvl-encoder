package de.vill.constraints.expression;

import de.vill.pbc.Literal;
import de.vill.pbc.PBConstraint;

import java.util.LinkedList;
import java.util.List;

public class NumberExpression extends de.vill.model.expression.NumberExpression implements IExpressionOpbEncodeable {
    public NumberExpression(double number) {
        super(number);
    }

    @Override
    public List<Literal> getAsSum(List<PBConstraint> additionalSubstitution) {
        List<Literal> result = new LinkedList<>();
        result.add(new Literal(getNumber(), null, true));
        return result;
    }
}
