package de.vill.constraints;

import de.vill.model.constraint.Constraint;
import de.vill.pbc.PBCLiteralConstraint;
import org.prop4j.Node;
import org.prop4j.Not;

import java.util.Map;

public class NotConstraint extends de.vill.model.constraint.NotConstraint implements IPbcEncodable {
    public NotConstraint(Constraint content) {
        super(content);
    }

    @Override
    public Node getNode() {
        var node = new Not(((IPbcEncodable)getContent()).getNode());
        return node;
    }

    public PBCLiteralConstraint extractTseitinSubConstraints(Map<Integer, Constraint> substitutionMapping) {
        PBCLiteralConstraint subContent = ((IPbcEncodable)getContent()).extractTseitinSubConstraints(substitutionMapping);
        subContent.toggleSign();
        return subContent;
    }
}
