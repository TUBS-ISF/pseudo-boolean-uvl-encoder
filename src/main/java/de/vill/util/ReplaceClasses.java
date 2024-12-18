package de.vill.util;

import de.vill.model.constraint.*;
import de.vill.model.expression.*;

public class ReplaceClasses {
    public static Constraint replace(Constraint constraint) {
        if (constraint instanceof AndConstraint){
            return new de.vill.constraints.AndConstraint(replace(((AndConstraint) constraint).getLeft()), replace(((AndConstraint) constraint).getRight()));
        }else if(constraint instanceof EquivalenceConstraint){
            return new de.vill.constraints.EquivalenceConstraint(replace(((EquivalenceConstraint) constraint).getLeft()), replace(((EquivalenceConstraint) constraint).getRight()));
        }else if(constraint instanceof GreaterEqualsEquationConstraint){
            return new de.vill.constraints.GreaterEqualsEquationConstraint(replace(((GreaterEqualsEquationConstraint)constraint).getLeft()), replace(((GreaterEqualsEquationConstraint)constraint).getRight()));
        }else if(constraint instanceof GreaterEquationConstraint){
            return new de.vill.constraints.GreaterEquationConstraint(replace(((GreaterEquationConstraint)constraint).getLeft()), replace(((GreaterEquationConstraint)constraint).getRight()));
        }else if(constraint instanceof ImplicationConstraint){
            return new de.vill.constraints.ImplicationConstraint(replace(((ImplicationConstraint) constraint).getLeft()), replace(((ImplicationConstraint) constraint).getRight()));
        }else if(constraint instanceof LiteralConstraint){
            return new de.vill.constraints.LiteralConstraint(((LiteralConstraint) constraint).getReference());
        }else if(constraint instanceof LowerEqualsEquationConstraint){
            return new de.vill.constraints.LowerEqualsEquationConstraint(replace(((LowerEqualsEquationConstraint)constraint).getLeft()), replace(((LowerEqualsEquationConstraint)constraint).getRight()));
        }else if(constraint instanceof LowerEquationConstraint){
            return new de.vill.constraints.LowerEquationConstraint(replace(((LowerEquationConstraint)constraint).getLeft()), replace(((LowerEquationConstraint)constraint).getRight()));
        }else if(constraint instanceof NotConstraint){
            return new de.vill.constraints.NotConstraint(replace(((NotConstraint) constraint).getContent()));
        }else if(constraint instanceof NotEqualsEquationConstraint){
            return new de.vill.constraints.NotEqualsEquationConstraint(replace(((NotEqualsEquationConstraint)constraint).getLeft()), replace(((NotEqualsEquationConstraint)constraint).getRight()));
        }else if(constraint instanceof OrConstraint){
            return new de.vill.constraints.OrConstraint(replace(((OrConstraint) constraint).getLeft()), replace(((OrConstraint) constraint).getRight()));
        }else if(constraint instanceof ParenthesisConstraint){
            return new de.vill.constraints.ParenthesisConstraint(replace(((ParenthesisConstraint) constraint).getContent()));
        }else {
            return constraint;
        }
    }

    public static Expression replace(Expression expression) {
        if (expression instanceof AddExpression){
            return new de.vill.constraints.expression.AddExpression(replace(((AddExpression) expression).getLeft()),replace(((AddExpression) expression).getRight()));
        }else if(expression instanceof DivExpression){
            return new de.vill.constraints.expression.DivExpression(replace(((DivExpression) expression).getLeft()),replace(((DivExpression) expression).getRight()));
        }else if(expression instanceof MulExpression){
            return new de.vill.constraints.expression.MulExpression(replace(((MulExpression) expression).getLeft()),replace(((MulExpression) expression).getRight()));
        }else if(expression instanceof LiteralExpression){
            return new de.vill.constraints.expression.LiteralExpression(((LiteralExpression) expression).getContent());
        }else if(expression instanceof NumberExpression){
            return new de.vill.constraints.expression.NumberExpression(((NumberExpression) expression).getNumber());
        }else if(expression instanceof ParenthesisExpression){
            return new de.vill.constraints.expression.ParenthesisExpression(replace(((ParenthesisExpression) expression).getContent()));
        }else if(expression instanceof SubExpression){
            return new de.vill.constraints.expression.SubExpression(replace(((SubExpression) expression).getLeft()),replace(((SubExpression) expression).getRight()));
        }else {
            return expression;
        }

    }
}
