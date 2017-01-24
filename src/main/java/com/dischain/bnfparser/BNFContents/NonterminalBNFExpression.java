package com.dischain.bnfparser.BNFContents;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NonterminalBNFExpression extends AbstractBNFExpression {

    public NonterminalBNFExpression () {
        this.variables = new LinkedList<AbstractMLVariable>();
    }

    public NonterminalBNFExpression (NonterminalMLVariable ... variables) {
        this();
        this.variables.addAll(Arrays.asList(variables));
    }

    public List<AbstractMLVariable> getVariables() {
        return variables;
    }

    public void addVariable(AbstractMLVariable variable) {
        if (!variable.isTerminal())
        this.variables.add(variable);
    }

    public boolean isTerminalExpression() {
        return false;
    }

    @Override
    public String toString() {
        String result = "";
        for (AbstractMLVariable var : variables)
            result += var.toString() + " ";
        return result;
    }
}
