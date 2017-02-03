package com.dischain.bnfparser.PDA;

import com.dischain.bnfparser.BNFContents.*;

import java.util.Stack;

public class PDA {

    private BNFGrammar grammar;

    private Stack<AbstractMLVariable> stack;

    public PDA (BNFGrammar grammar) {
        this.grammar = grammar;
        stack = new Stack<AbstractMLVariable>();
        String  initialRuleName = grammar.getInitialRule().getRuleName();
        AbstractMLVariable initialVariable = grammar.getNonterm(initialRuleName);
        stack.push(initialVariable);
    }

    public boolean acceptSequence(String word) {

        //while (true) {

            for (AbstractBNFExpression expr : grammar.getRule(stack.peek().getVariable()).getExpressions()) {
                System.out.println(expr);
            }
        //}

        return false;
    }

    public void depthFirstSearch (String word, AbstractBNFExpression expression, Boolean contains) {

        if (expression.isTerminalExpression()) {
            System.out.println("Нашли терминальное выражение");
            if (expression.equalsTerm(word)) {
                contains = true;
                return;
            }
        }

        else if (!expression.isTerminalExpression()) {
            for (AbstractMLVariable var : expression.getVariables()) {
                depthFirstSearch (word, grammar.getRule(var), false);
            }
        }
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
