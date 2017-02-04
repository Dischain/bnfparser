package com.dischain.bnfparser.Util;

import com.dischain.bnfparser.BNFContents.AbstractBNFExpression;
import com.dischain.bnfparser.BNFContents.AbstractBNFRule;
import com.dischain.bnfparser.BNFContents.AbstractMLVariable;
import com.dischain.bnfparser.BNFContents.BNFGrammar;
import com.dischain.bnfparser.BaseGrammar.BaseGrammar;

/**
 * Простой ориентированный граф, выполняющий поиск вглубину на оринетированном
 * ациклическом графе, построенном на основании параметров метода
 * {@link #depthFirstSearch(String, AbstractBNFExpression)} - выражение правила
 * грамматики.
 *
 * В качестве структуры данных графа подразумевается дерево, которое может
 * быть рекурсивно построено при раскрытии всех нетерминальных переменных,
 * входящий в состав указанного выражения.
 *
 * @see com.dischain.bnfparser.BNFContents.NonterminalBNFExpression
 */
public class DGraph {

    private BNFGrammar grammar;

    private boolean found;

    public DGraph (BNFGrammar grammar) {
        this.grammar = grammar;
        this.found = false;
    }

    /**
     * Реализует простой поиск вглубину некоторого слова {@code word} во всех раскрытиях
     * правил металингвистических переменных, которые входят в заданное выражение
     * {@code expression}
     *
     * В случае совпадения переменная {@link #found} выставляется в {@code true}
     * @param word искомое слово
     * @param expression выражение, в котором выполняется поиск.
     */
    public void depthFirstSearch (String word, AbstractBNFExpression expression) {
        found = false;
        if (expression.isTerminalExpression()) {
            if (expression.equalsTerm(word)) {
                this.found = true;
            }
        }

        else if (!expression.isTerminalExpression()) {
            for (AbstractMLVariable var : expression.getVariables()) {
                System.out.println("Ищем правило для: " + var);
                if (BaseGrammar.isBaseGrammarSymbol(var)) {
                    System.out.println("Это символ базовой грамматики: " + var);
                    continue;
                }
                depthFirstSearch (word, grammar.getRule(var));
            }
        }
    }

    /**
     * Реализует простой поиск вглубину некоторого слова {@code word} во всех раскрытиях
     * правила заданной грамматики {@code fromRule}
     *
     * В случае совпадения переменная {@link #found} выставляется в {@code true}
     * @param word искомое слово
     * @param fromRule правило грамматики, в котором выполняется поиск.
     */
    private void depthFirstSearch(String word, AbstractBNFRule fromRule) {
        if (fromRule.isTerminalRule()){
            System.out.println("Поиск вглубину для " + fromRule + " " + fromRule.isTerminalRule());
            if (fromRule.containsTerm(word)){
                System.out.println("Правило " + fromRule.getRuleName() + " содержит искомое слово");
                this.found = true;
            }
        }

        else if (!fromRule.isTerminalRule()) {
            for (AbstractBNFExpression expr : fromRule.getExpressions()) {
                for (AbstractMLVariable var : expr.getVariables()) {
                    if (BaseGrammar.isBaseGrammarSymbol(var)) {
                        System.out.println("Это символ базовой грамматики: " + var);
                        continue;
                    }
                    depthFirstSearch(word, grammar.getRule(var.getVariable()));
                }
            }
        }
    }

    public boolean isFound() { return found; }
}
