package com.dischain.bnfparser.PDA;

import com.dischain.bnfparser.BNFContents.*;
import com.dischain.bnfparser.BaseGrammar.BaseGrammar;
import com.dischain.bnfparser.Util.DGraph;

import java.beans.Expression;
import java.util.Stack;

public class PDA {

    private BNFGrammar grammar;

    public DGraph graph;

    private Stack<AbstractMLVariable> stack;

    public PDA (BNFGrammar grammar) {
        graph = new DGraph(grammar);
        this.grammar = grammar;
        stack = new Stack<AbstractMLVariable>();
        String  initialRuleName = grammar.getInitialRule().getRuleName();
        AbstractMLVariable initialVariable = grammar.getNonterm(initialRuleName);
        stack.push(initialVariable);
    }

    public boolean acceptSequence(String word) {

        while (true) {
            AbstractMLVariable curMLVar = stack.peek();

            if (!curMLVar.isTerminal() || !BaseGrammar.isBaseGrammarSymbol(curMLVar)) {
                AbstractBNFRule curRule = grammar.getRule(curMLVar.getVariable());
                AbstractBNFExpression expanded = null;
                for (AbstractBNFExpression expr : curRule.getExpressions()) {
                    Boolean contains = false;
                    graph.depthFirstSearch (word, expr);
                    if (contains) {
                        expanded = expr;
                    }
                }
                if (expanded != null) {
                    stack.pop();
                    int size = expanded.getVariables().size();
                    for (int i = size - 1; i >= 0; i --)
                        stack.push(expanded.getVariables().get(i));
                }
            }
            if (curMLVar.isTerminal() !BaseGrammar.isBaseGrammarSymbol()) {
                if (curMLVar.getVariable().equals(curWord) ) {
                    stack.pop();
                    nextWord();
                }
            }

            if (stack.isEmpty()) return true;
            else return false;
        }
    }

    public void depthFirstSearch (String word, AbstractBNFExpression expression) {

        if (expression.isTerminalExpression()) {
            System.out.println("Нашли терминальное выражение");
            if (expression.equalsTerm(word)) {
                return;
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

    /*public void depthFirstSearch (String word, AbstractBNFRule fromRule) {
        depthFirstSearch(word, fromRule);
    }*/
}
