package com.dischain.bnfparser.BNFContents;


/**
 * Представляет базовый класс для всех металингвистических
 * символов КС-грамматики.
 *
 * @see NonterminalMLVariable
 * @see NonterminalMLVariable
 * @see com.dischain.bnfparser.BaseGrammar.BaseGrammarMLVariable
 */
public abstract class AbstractMLVariable {

    protected String variable;

    public AbstractMLVariable() {
        variable = "";
    }

    public AbstractMLVariable(String variable) {
        this.variable = variable;
    }

    public void setVariable(String variable) {
        if (!variable.equals("")) {
            this.variable = variable;
        }
    }

    public String getVariable() {
        return variable;
    }

    /**
     * Проверяет, является ли данный экземпляр класса терминльным символом
     * грамматики
     * @return <code>true</code>, если является и <code>false</code>, если нет
     */
    public abstract boolean isTerminal ();
}
