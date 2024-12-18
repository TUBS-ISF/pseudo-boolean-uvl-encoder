package de.vill.constraints;

import de.vill.model.constraint.Constraint;
import de.vill.pbc.PBCLiteralConstraint;
import org.prop4j.Node;

import java.util.Map;

public class ParenthesisConstraint extends de.vill.model.constraint.ParenthesisConstraint implements IPbcEncodable {
    public ParenthesisConstraint(Constraint content) {
        super(content);
    }

    public PBCLiteralConstraint extractTseitinSubConstraints(Map<Integer, Constraint> substitutionMapping) {
        return ((IPbcEncodable)getContent()).extractTseitinSubConstraints(substitutionMapping);
    }

    @Override
    public Node getNode() {
        return ((IPbcEncodable)getContent()).getNode();
    }
}
