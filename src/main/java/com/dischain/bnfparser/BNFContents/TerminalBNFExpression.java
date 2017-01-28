package com.dischain.bnfparser.BNFContents;

import java.util.ArrayList;
import java.util.List;


public class TerminalBNFExpression extends AbstractBNFExpression {

    private static int SIZE = 1;

    public TerminalBNFExpression () {
        this.variables = new ArrayList<AbstractMLVariable>(SIZE);
    }

    public TerminalBNFExpression (AbstractMLVariable variable) {
        this();
        if (variable.isTerminal())
            this.variables.add(variable);
    }

    public TerminalBNFExpression (TerminalMLVariable variable) {
        this();
        this.variables.add(variable);
    }

    public List<AbstractMLVariable> getVariables() {
        return this.variables;
    }

    /**
     * Добавляет терминальную металингвистическую переменную в выражение.
     * Так как терминальное выражение может содержать только одну переменную,
     * при добавлении переменной в уже заполненное выражение старое значение
     * стирается и добавляется новове.
     * @param variable металингвистическая переменная, которая будет добавлена
     */
    public void addVariable(AbstractMLVariable variable) {
        if (variable.isTerminal ()) {
            if (this.variables.isEmpty())
                this.variables.add(variable);
            else {
                this.variables.clear();
                this.variables.add(variable);
            }
        }
    }

    public boolean isTerminalExpression() {
        return true;
    }

    @Override
    public String toString() {
        if(!variables.isEmpty()) {
            return variables.get(0).toString() + " ";
        }
        return null;
    }
}
