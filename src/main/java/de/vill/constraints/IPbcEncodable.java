package de.vill.constraints;

import de.vill.model.constraint.Constraint;
import de.vill.pbc.PBCLiteralConstraint;
import org.prop4j.Node;

import java.util.Map;

public interface IPbcEncodable {

    /**
     * Gets the part of the formula as prop4j node. This is used to encode a formula as CNF.
     *
     * @return the node associated with this sub-formula.
     */
    Node getNode();

    /**
     * Extracts Tseitin sub-constraints form a formula.
     *
     * @param substitutionMapping a map where the key is an index of the substitution variable and the value is the subformula that is substituted.
     * @return the substitution that represents the whole formula as pseudo-boolean literal
     */
    PBCLiteralConstraint extractTseitinSubConstraints(Map<Integer, Constraint> substitutionMapping);
}
