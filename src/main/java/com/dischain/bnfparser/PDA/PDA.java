package com.dischain.bnfparser.PDA;

import com.dischain.bnfparser.BNFContents.*;
import com.dischain.bnfparser.BaseGrammar.BaseGrammar;
import com.dischain.bnfparser.Util.DGraph;
import com.dischain.bnfparser.Util.SequenceHandler;

import java.util.Stack;

public class PDA {

    private BNFGrammar grammar;

    private SequenceHandler handler;

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

        handler = new SequenceHandler(word);
        String curWord = handler.getNext();
        while (true/*handler.containsElse()*/) {
            AbstractMLVariable curMLVar = stack.peek();
            System.out.println("Current variable: " + curMLVar);
            System.out.println("Current word: " + curWord);
            if (!curMLVar.isTerminal() && !BaseGrammar.isBaseGrammarSymbol(curMLVar)) {
                AbstractBNFRule curRule = grammar.getRule(curMLVar.getVariable());
                System.out.println("Current rule: " + curRule);
                AbstractBNFExpression expanded = null;
                for (AbstractBNFExpression expr : curRule.getExpressions()) {
                    graph.depthFirstSearch (curWord, expr);
                    if (graph.isFound()) {
                        System.out.println("Найдено " + expr);
                        expanded = expr;
                        break;
                    }
                }
                /*for (AbstractBNFExpression expr : curRule.getExpressions()) {
                    for (AbstractMLVariable var : expr.getVariables()) {
                        *//*if (BaseGrammar.isBaseGrammarSymbol(var)) {
                            System.out.println("Это символ базовой грамматики: " + var);
                            continue;
                        }*//*
                        graph.depthFirstSearch(curWord, grammar.getRule(var));
                        if (graph.isFound()) {
                            expanded = expr;
                            break;
                        }
                    }
                }*/
                System.out.println("Следующее выражение: " + expanded);
                if (expanded != null) {
                    stack.pop();
                    int size = expanded.getVariables().size();
                    for (int i = size - 1; i >= 0; i --) {
                        System.out.println("Заталкивается " + expanded.getVariables().get(i));
                        stack.push(expanded.getVariables().get(i));
                    }
                } else {
                    //Не нашли
                    return false;
                }
            }
            if (curMLVar.isTerminal() || BaseGrammar.isBaseGrammarSymbol(curMLVar)) {
                if (curMLVar.getVariable().equals(curWord)
                        || BaseGrammar.equals(curWord, curMLVar)) {
                    if (handler.containsElse())
                        curWord = handler.getNext();
                    stack.pop();
                    //handler.getNext();
                } else {
                    return false;
                }
            }
            if (stack.isEmpty() && handler.containsElse()) {
                System.out.println("sdasdasd");
                break;
            }
            if (stack.isEmpty() /*&& !handler.containsElse()*/) return true;
            System.out.println("Есть ли еще правила " + handler.containsElse());
            System.out.println();
        }
        return false;
    }
}
