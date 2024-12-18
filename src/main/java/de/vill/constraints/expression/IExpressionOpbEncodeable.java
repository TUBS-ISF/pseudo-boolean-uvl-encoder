package de.vill.constraints.expression;

import de.vill.pbc.Literal;
import de.vill.pbc.PBConstraint;

import java.util.List;

public interface IExpressionOpbEncodeable {
    List<Literal> getAsSum(List<PBConstraint> additionalSubstitution);
}
