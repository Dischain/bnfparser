package com.dischain.bnfparser.BNFContents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Реализует нетерминальное правило КС-грамматики. Может быть описано
 * множеством нетерминальных выражений. Допускается рекурсивное определение,
 * например:
 * <p><code>SOMERULE = ENOTHER_RULE | SOMERULE ENOTHER_RULE</code>
 *
 * @see NonterminalBNFExpression
 */
public class NonterminalBNFRule extends AbstractBNFRule{

    public NonterminalBNFRule() {
        super();
    }

    public NonterminalBNFRule(String ruleName) {
        super(ruleName);
    }

    public NonterminalBNFRule(String ruleName, NonterminalBNFExpression ... expressions) {
        super(ruleName);
        this.expressions.addAll(Arrays.asList(expressions));
    }

    public NonterminalBNFRule(String ruleName, List<AbstractBNFExpression> expressions) {
        super(ruleName);
        for (AbstractBNFExpression expr : expressions) {
            if (!expr.isTerminalExpression())
                this.expressions.add(expr);
        }
    }

    public boolean isTerminalRule() {
        return false;
    }

    public List<List<AbstractMLVariable>> getListsOfVariables() {
        ArrayList<List<AbstractMLVariable>> listOfVars =
                new ArrayList<List <AbstractMLVariable>>();

        for (AbstractBNFExpression expr : expressions) {
            listOfVars.add(expr.getVariables());
        }
        return listOfVars;
    }

    public void addExpressions(AbstractBNFExpression ... expressions) {
        for (AbstractBNFExpression expr : expressions) {
            if (!expr.isTerminalExpression())
                this.expressions.add(expr);
        }
    }

    public boolean containsTerm(String term) {
        return false;
    }

    @Override
    public String toString() {
        String result = ruleName.toUpperCase() + " = ";
        boolean flag = true;
        for (AbstractBNFExpression expr : this.expressions) {
            if (!flag) result += " | ";
            result += expr.toString();
            flag = false;
        }
        return result;
    }
}
