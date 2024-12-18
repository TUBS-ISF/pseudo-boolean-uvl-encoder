package de.vill.constraints;

import de.vill.model.building.VariableReference;
import de.vill.model.constraint.Constraint;
import de.vill.pbc.PBCLiteralConstraint;
import org.prop4j.Literal;
import org.prop4j.Node;

import java.util.Map;

public class LiteralConstraint extends de.vill.model.constraint.LiteralConstraint implements IPbcEncodable {
    public LiteralConstraint(VariableReference reference) {
        super(reference);
    }

    @Override
    public PBCLiteralConstraint extractTseitinSubConstraints(Map<Integer, Constraint> substitutionMapping) {
        return new PBCLiteralConstraint(this);
    }

    @Override
    public Node getNode() {
        var node = new Literal(getReference().getIdentifier(), true);
        return node;
    }
}
