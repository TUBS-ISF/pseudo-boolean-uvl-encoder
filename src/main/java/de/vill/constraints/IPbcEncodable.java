package de.vill.constraints;

import de.vill.model.constraint.Constraint;
import de.vill.pbc.PBCLiteralConstraint;
import org.prop4j.Node;

import java.util.Map;

public interface IPbcEncodable {
    Node getNode();
    PBCLiteralConstraint extractTseitinSubConstraints(Map<Integer, Constraint> substitutionMapping);
}
