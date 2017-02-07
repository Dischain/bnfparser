package com.dischain.bnfparser.BNFContents;

import com.dischain.bnfparser.Util.TrieTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Реализация терминального правила КС-грамматики. Иными словами
 * данное правило может определять правила вывода только для терминальных
 * символов КС-грамматики.
 * <p>Например, данное правило является терминальным:</p>
 * <p><code>RULE = "term1" | "tern2" | "term3"</code></p>
 * Может содержать множество только выражений, являющимхся терминальными
 * ({@link TerminalBNFExpression}).
 *
 * <p>Так как данная реализация парсера по КС-грамматике предоставляет
 * терминальные выражения, состоящие только из одного терминального символа,
 * определение терминального правила не может быть записано в следующе форме:</p>
 * <p><code>RULE = "term1" "term2" | "term3" term4"</code></p>
 * <p> - данный API попросту не позволит этого сделать и выбросит
 * {@link com.dischain.bnfparser.Exception.BNFParseException}
 *
 * <p>Терминальное правило содержит в себе {@link TrieTree} - префиксное
 * дерево, позволяющее определять наличие некоторой цепочки в множестве
 * терминалов, задаваемом данным правилом, за {@code O(n)}, где {@code n} -
 * длина указанной цепочки. </p>
 * 
 * @see TerminalBNFExpression
 * @see TerminalMLVariable
 */
public class TerminalBNFRule extends AbstractBNFRule {

    private TrieTree<String> trieTree;

    public TerminalBNFRule() {
        super();
    }

    public TerminalBNFRule(String ruleName) {
        super(ruleName);
    }

    /**
     * Строит терминальное правило по заданному имени правила и множеству выражений
     * периодической длины
     *
     * @param ruleName имя правила
     * @param expressions список выражений правила
     */
    public TerminalBNFRule(String ruleName, TerminalBNFExpression ... expressions) {
        super(ruleName);
        this.expressions.addAll(Arrays.asList(expressions));

        constructTrieTree(expressions);
    }

    /**
     * Строит терминальное правило по заданному имени правила и списку его выражений
     *
     * @param ruleName имя правила
     * @param expressions список выражений правила
     */
    public TerminalBNFRule(String ruleName, List<AbstractBNFExpression> expressions) {
        super(ruleName);
        for (AbstractBNFExpression expr : expressions) {
            if (expr.isTerminalExpression())
                this.expressions.add(expr);
        }

        constructTrieTree(expressions);
    }

    /**
     * Строит префиксное дерево {@link TrieTree} по множеству указанных выражений,
     * входящих в состав данного правила
     *
     * @param expressions список выражений, составляющих данное правило
     */
    private void constructTrieTree(List<AbstractBNFExpression> expressions) {
        if (trieTree == null)
            trieTree = new TrieTree<String>();
        for (AbstractBNFExpression expr : expressions) {
            String variable = expr.getVariables().get(0).getVariable();
            trieTree.put(variable);
        }
    }

    /**
     * Строит префиксное дерево {@link TrieTree} по множеству указанных терминалов,
     * входящих в состав данного правила
     *
     * @param expressions множество терминалов, определяемых данным правилом
     */
    private void constructTrieTree(AbstractBNFExpression ... expressions) {
        if (trieTree == null)
            trieTree = new TrieTree<String>();
        for (AbstractBNFExpression expr : expressions) {
            String variable = expr.getVariables().get(0).getVariable();
            trieTree.put(variable);
        }
    }

    private TrieTree<String> getTrieTree () {
        if (trieTree == null)
            trieTree = new TrieTree<String>();
        return trieTree;
    }

    /**
     * Добавляет выражение к терминальному правилу грамматики. В случае, если
     * выражение не является терминальным, выражение отвергается. В противном
     * случае выражение добавится к правилу и обновится префиксное дерево.
     *
     * @param expressions выражения, добавляемые к правилу. Может содержать периодическое
     * количество аргументов.
     */
    public void addExpressions(AbstractBNFExpression... expressions) {
        for (AbstractBNFExpression expr : expressions) {
            if (expr.isTerminalExpression())
                this.expressions.add(expr);
        }
        constructTrieTree(expressions);
    }

    /**
     * Проверяет, является ли данное правило терминальным
     *
     * @return {@code true}, если правило терминальное, {@code false}
     * в противном случае.
     */
    public boolean isTerminalRule() {
        return true;
    }

    /**
     * Возвращает двумерный массив терминальных символов данного правила
     * в виде множества множеств терминалов.
     *
     * @return множество множеств терминалов данного правила.
     */
    public List<List<AbstractMLVariable>> getListsOfVariables() {
        ArrayList<List<AbstractMLVariable>> listOfVars =
                new ArrayList<List <AbstractMLVariable>>();

        for (AbstractBNFExpression expr : expressions) {
            listOfVars.add(expr.getVariables());
        }
        return listOfVars;
    }

    /**
     * Проверяет наличие терминального символа в терминальном правиле.
     * @param term терминальная последовательность
     * @return {@code true} если данная терминальная последовательность содержится
     * в данном правиле, {@code false}, если нет
     */
    public boolean containsTerm(String term) {
        return trieTree.contains(term);
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
