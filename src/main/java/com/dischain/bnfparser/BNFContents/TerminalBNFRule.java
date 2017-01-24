package com.dischain.bnfparser.BNFContents;

import com.dischain.bnfparser.Util.TrieTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TerminalBNFRule extends AbstractBNFRule {

    private TrieTree<String> trieTree;

    public TerminalBNFRule() {
        super();
    }

    public TerminalBNFRule(String ruleName) {
        super(ruleName);
    }

    public TerminalBNFRule(String ruleName, TerminalBNFExpression ... expressions) {
        super(ruleName);
        this.expressions.addAll(Arrays.asList(expressions));

        constructTrieTree(expressions);
    }

    private void constructTrieTree(AbstractBNFExpression ... expressions) {
        if (trieTree == null)
            trieTree = new TrieTree<String>();
        for (AbstractBNFExpression expr : expressions) {
            String variable = expr.getVariables().get(0).getVariable();
            trieTree.put(variable);
        }
    }

    public TrieTree<String> getTrieTree () {
        if (trieTree == null)
            trieTree = new TrieTree<String>();
        return trieTree;
    }

    public void addExpressions(AbstractBNFExpression... expressions) {
        for (AbstractBNFExpression expr : expressions) {
            if (expr.isTerminalExpression())
                this.expressions.add(expr);
        }
        constructTrieTree(expressions);
    }

    public boolean isTerminalRule() {
        return true;
    }

    public List<List<AbstractMLVariable>> getListsOfVariables() {
        ArrayList<List<AbstractMLVariable>> listOfVars =
                new ArrayList<List <AbstractMLVariable>>();

        for (AbstractBNFExpression expr : expressions) {
            listOfVars.add(expr.getVariables());
        }
        return listOfVars;
    }

    public static void main(String[] args) {
        TerminalBNFRule rule = new TerminalBNFRule("STR", new TerminalBNFExpression(new TerminalMLVariable("asda")), new TerminalBNFExpression(new TerminalMLVariable("asdanya")));
        //rule.addExpressions(new TerminalBNFExpression(new TerminalMLVariable("asda")), new TerminalBNFExpression(new TerminalMLVariable("asda")));
        //rule.setRuleName("SPP");
        System.out.println(rule.getListsOfVariables());
        System.out.println(rule.getTrieTree().get("asd"));
    }
}
