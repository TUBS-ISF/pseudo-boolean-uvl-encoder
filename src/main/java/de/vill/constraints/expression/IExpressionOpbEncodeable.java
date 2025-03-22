package de.vill.constraints.expression;

import de.vill.pbc.Literal;
import de.vill.pbc.PBConstraint;

import java.util.List;

public interface IExpressionOpbEncodeable {

    /**
     * Converts the expression into a sum a list of literals which is interpreted as a sum.
     * This method is used recursively to encode an arbitrary expression as sum (which can be interpreted as a pseudo-boolean constraint)
     *
     *
     * @param additionalSubstitution a list of pseudo-boolean constraints that are created during
     * the transformation. These constraints are independet of other substitutions and are added to the final pseudo-boolean constraint encoding of the feature model without modification.
     * @return a list of {@code Literal} objects representing the sum. This sum is changed to a pseudo-boolean constraint that
     * is only active if the current substitution variable is active.
     */
    List<Literal> getAsSum(List<PBConstraint> additionalSubstitution);
}
