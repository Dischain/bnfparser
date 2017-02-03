package com.dischain.bnfparser.Util;

import com.dischain.bnfparser.BNFContents.AbstractBNFExpression;
import com.dischain.bnfparser.BNFContents.AbstractBNFRule;
import com.dischain.bnfparser.BNFContents.AbstractMLVariable;
import com.dischain.bnfparser.BNFContents.BNFGrammar;

public class DGraph {

    private BNFGrammar grammar;

    public boolean found;

    public DGraph (BNFGrammar grammar) {
        this.grammar = grammar;
        this.found = false;
    }

    public void depthFirstSearch (String word, AbstractBNFExpression expression) {
        found = false;
        if (expression.isTerminalExpression()) {
            System.out.println("Нашли терминальное выражение");
            if (expression.equalsTerm(word)) {
                this.found = true;
            }
        }

        else if (!expression.isTerminalExpression()) {
            for (AbstractMLVariable var : expression.getVariables()) {
                depthFirstSearch (word, grammar.getRule(var));
            }
        }
    }

    private void depthFirstSearch(String word, AbstractBNFRule fromRule) {
        String ruleName = "";

        if (fromRule.isTerminalRule()){
            System.out.println("Нашли терминальное правило");
            if (fromRule.containsTerm(word)){
                ruleName = fromRule.getRuleName();
                this.found = true;
                System.out.println("Нашли в правиле " + ruleName);
            }
        }

        else if (!fromRule.isTerminalRule()) {
            for (AbstractBNFExpression expr : fromRule.getExpressions()) {
                for (AbstractMLVariable var : expr.getVariables()) {
                    depthFirstSearch(word, grammar.getRule(var.getVariable()));
                }
            }
        }
    }
}
