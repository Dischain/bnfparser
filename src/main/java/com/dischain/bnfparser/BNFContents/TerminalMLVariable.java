package com.dischain.bnfparser.BNFContents;

public class TerminalMLVariable extends AbstractMLVariable {

    public TerminalMLVariable() {
        super();
    }

    public TerminalMLVariable(String variable) {
        super(variable);
    }

    public boolean isTerminal() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return this.variable.equals((String) obj);
    }

    @Override
    public String toString() {
        return '"' + getVariable() + '"';
    }
}
