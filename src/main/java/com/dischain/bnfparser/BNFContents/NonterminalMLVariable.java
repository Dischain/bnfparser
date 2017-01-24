package com.dischain.bnfparser.BNFContents;

public class NonterminalMLVariable extends AbstractMLVariable {

    public NonterminalMLVariable() {
        super();
    }

    public NonterminalMLVariable(String variable) {
        super(variable);
    }

    public boolean isTerminal() {
        return false;
    }

    @Override
    public String toString() {
        return getVariable().toUpperCase();
    }
}
