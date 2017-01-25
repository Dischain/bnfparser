package com.dischain.bnfparser.BNFContents;

/**
 * Реализация терминальной металингвистической переменной КС-грамматики.
 * Может содержать в себе любую последовательность символов, не разделенных
 * символами <code>\n</code>, <code>\t</code> и любыми пробельными символами.
 */
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
