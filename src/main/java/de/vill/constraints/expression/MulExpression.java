package de.vill.constraints.expression;

import de.vill.model.expression.Expression;
import de.vill.pbc.Literal;
import de.vill.pbc.PBConstraint;
import de.vill.encoding.SubstitutionVariableIndex;

import java.util.LinkedList;
import java.util.List;

public class MulExpression extends de.vill.model.expression.MulExpression implements IExpressionOpbEncodeable {
    public MulExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public List<Literal> getAsSum(List<PBConstraint> additionalSubstitution) {
        var leftSum = ((IExpressionOpbEncodeable)getLeft()).getAsSum(additionalSubstitution);
        var rightSum = ((IExpressionOpbEncodeable)getRight()).getAsSum(additionalSubstitution);
        List<Literal> result = new LinkedList<>();
        SubstitutionVariableIndex substitutionVariableIndex = SubstitutionVariableIndex.getInstance();
        for (int i=0;i<leftSum.size();i++){
            for (int j=0;j<rightSum.size();j++){
                if (leftSum.get(i).name == null) {
                    rightSum.get(j).factor *= leftSum.get(i).factor;
                    result.add(rightSum.get(j));
                }else if (rightSum.get(j).name == null) {
                    leftSum.get(i).factor *= rightSum.get(j).factor;
                    result.add(leftSum.get(i));
                }else {
                    Literal l = new Literal();
                    l.factor = leftSum.get(i).factor * rightSum.get(j).factor;
                    var subIndex = substitutionVariableIndex.getIndex();
                    l.name = "x_" + subIndex;
                    result.add(l);
                    additionalSubstitution.addAll(getSubstitutionConstraints(leftSum.get(i).name, rightSum.get(j).name, l.name));
                }
            }
        }
        return result;
    }

    private List<PBConstraint> getSubstitutionConstraints(String a, String b, String c){
        List<PBConstraint> result = new LinkedList<>();
        PBConstraint PBConstraint1 = new PBConstraint();
        PBConstraint1.literalList = new LinkedList<>();
        PBConstraint1.k = 0;
        PBConstraint1.literalList.add(new Literal(1, a, true));
        PBConstraint1.literalList.add(new Literal(1, b, true));
        PBConstraint1.literalList.add(new Literal(-2, c, true));
        result.add(PBConstraint1);
        PBConstraint PBConstraint2 = new PBConstraint();
        PBConstraint2.literalList = new LinkedList<>();
        PBConstraint2.k = -1;
        PBConstraint2.literalList.add(new Literal(-1, a, true));
        PBConstraint2.literalList.add(new Literal(-1, b, true));
        PBConstraint2.literalList.add(new Literal(2, c, true));
        result.add(PBConstraint2);
        return result;
    }
}
