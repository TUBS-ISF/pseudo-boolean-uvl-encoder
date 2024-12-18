package de.vill.constraints.expression;

import com.google.common.collect.Sets;
import de.vill.model.expression.Expression;
import de.vill.pbc.Literal;
import de.vill.pbc.PBConstraint;
import de.vill.util.SubstitutionVariableIndex;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static de.vill.util.Utility.substitutionConstraint;

public class DivExpression extends de.vill.model.expression.DivExpression implements IExpressionOpbEncodeable {
    public DivExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public List<Literal> getAsSum(List<PBConstraint> additionalSubstitution) {
        List<Literal> result = new LinkedList<>();
        List<Literal> numeratorSum = ((IExpressionOpbEncodeable)getLeft()).getAsSum(additionalSubstitution);
        List<Literal> denominatorSum = ((IExpressionOpbEncodeable)getRight()).getAsSum(additionalSubstitution);
        SubstitutionVariableIndex substitutionVariableIndex = SubstitutionVariableIndex.getInstance();
        for (Literal l : numeratorSum) {
            Set<Set<Literal>> literalCombinations = getLiteralCombinations(new HashSet<Literal>(denominatorSum));
            for (Set<Literal> combination : literalCombinations) {
                Literal newSummand = new Literal();
                newSummand.factor = l.factor;
                double denominatorFactorSum = 0.0;
                for (Literal denominatorLiteral : combination) {
                    denominatorFactorSum += denominatorLiteral.factor;
                }
                newSummand.factor /= denominatorFactorSum;
                var subIndex = substitutionVariableIndex.getIndex();
                newSummand.name = "x_" + subIndex;
                result.add(newSummand);
                PBConstraint denominatorConstraint = featureCombinationToPBConstraint(combination, denominatorSum);
                if (l.name != null) {
                    denominatorConstraint.literalList.add(new Literal(1, l.name, l.sign));
                    denominatorConstraint.k += 1;
                }

                additionalSubstitution.addAll(substitutionConstraint(denominatorConstraint, newSummand.name));
            }
        }
        return result;
    }

    private Set<Set<Literal>> getLiteralCombinations(Set<Literal> literals) {
        Set<Set<Literal>> literalCombinations = new HashSet<>();
        for (int i = 1; i <= literals.size(); i++) {
            literalCombinations.addAll(Sets.combinations(literals, i));
        }
        return literalCombinations;
    }

    private PBConstraint featureCombinationToPBConstraint(Set<Literal> takenLiterals, List<Literal> allLiterals) {
        PBConstraint pbConstraint = new PBConstraint();
        pbConstraint.literalList = new LinkedList<>();
        pbConstraint.k = allLiterals.size();
        for (Literal literal : allLiterals) {
            if (literal.name != null){
                if (takenLiterals.contains(literal)) {
                    //literal positive in result
                    Literal newLiteral = new Literal();
                    newLiteral.name = literal.name;
                    newLiteral.factor = 1;
                    newLiteral.sign = literal.sign;
                    pbConstraint.literalList.add(newLiteral);
                }else {
                    //literal negative in result
                    Literal negatedLiteral = new Literal();
                    negatedLiteral.name = literal.name;
                    negatedLiteral.factor = 1;
                    negatedLiteral.sign = !literal.sign;
                    pbConstraint.literalList.add(negatedLiteral);
                }
            }
        }
        return pbConstraint;
    }
}
