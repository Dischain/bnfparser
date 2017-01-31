package com.dischain.bnfparser.PDA;

import com.dischain.bnfparser.BNFContents.AbstractBNFExpression;
import com.dischain.bnfparser.BNFContents.AbstractBNFRule;
import com.dischain.bnfparser.BNFContents.AbstractMLVariable;
import com.dischain.bnfparser.BNFContents.BNFGrammar;

public class PDA {

    private BNFGrammar grammar;

    public PDA (BNFGrammar grammar) {
        this.grammar = grammar;
    }

    public void depthFirstSearch(String word, AbstractBNFRule fromRule, Boolean found) {
        String ruleName = "";

        if (fromRule.isTerminalRule()){
            System.out.println("Нашли терминальное правило");
            if (fromRule.containsTerm(word)){
                ruleName = fromRule.getRuleName();
                System.out.println("Нашли в правиле " + fromRule);
                found = true;
            }
        }

        else if (!fromRule.isTerminalRule() && !found) {
            for (AbstractBNFExpression expr : fromRule.getExpressions()) {
                for (AbstractMLVariable var : expr.getVariables()) {
                    depthFirstSearch(word, grammar.getRule(var.getVariable()), found);
                }
            }
        }
    }

    public void depthFirstSearch (String word, AbstractBNFRule fromRule) {
        depthFirstSearch(word, fromRule, false);
        /*String ruleName = "";
        if (fromRule.isTerminalRule()){
            System.out.println("Нашли терминальное правило");
            if (fromRule.containsTerm(word)){
                ruleName = fromRule.getRuleName();
                System.out.println("Нашли в правиле " + fromRule);
                return;
            }
        }

        else if (!fromRule.isTerminalRule() && ruleName.equals("")) {
            for (AbstractBNFExpression expr : fromRule.getExpressions()) {
                for (AbstractMLVariable var : expr.getVariables()) {
                    depthFirstSearch(word, grammar.getRule(var.getVariable()));
                }
            }
        }*/
    }
}
