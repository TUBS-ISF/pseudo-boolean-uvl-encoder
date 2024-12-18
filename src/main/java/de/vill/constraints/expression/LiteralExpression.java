package de.vill.constraints.expression;

import de.vill.model.Attribute;
import de.vill.model.building.VariableReference;
import de.vill.pbc.Literal;
import de.vill.pbc.PBConstraint;

import java.util.LinkedList;
import java.util.List;

public class LiteralExpression extends de.vill.model.expression.LiteralExpression implements IExpressionOpbEncodeable {
    public LiteralExpression(Boolean value) {
        super(value);
    }

    public LiteralExpression(VariableReference reference) {
        super(reference);
    }

    @Override
    public List<Literal> getAsSum(List<PBConstraint> additionalSubstitution) {
        Literal l = new Literal();
        var attribute = (Attribute<?>)getContent();
        l.name = attribute.getFeature().getFeatureName();
        l.factor = attribute.getValue() instanceof Long ? ((Long) attribute.getValue()).doubleValue() : (double) attribute.getValue();
        List<Literal> result = new LinkedList<>();
        result.add(l);
        return result;
    }
}
