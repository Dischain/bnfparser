package com.dischain.bnfparser.BNFContents;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация терминального выражения КС-грамматики.
 * Терминальные выражения составляют основу терминальных правил
 * {@link TerminalBNFRule} и содержат не более чем одну терминальную
 * переменную {@link TerminalMLVariable}.
 *
 * <p>Пример правильного терминального выражения:
 * ["term"]. Тогда, руководствуясь такой нотацией, терминальное правило
 * можно определить следующим образом:</p>
 * <p><code>TERM_RULE = {["tern1"], ["term2"], ["term3]}</code></p>
 *
 * <p>Соответственно огда неправильное терминальное выражение
 * можно условно описать так: <code>["term1" "term2" ... "termn"]</code></p>
 *
 * @see TerminalBNFRule
 * @see TerminalMLVariable
 */
public class TerminalBNFExpression extends AbstractBNFExpression {

    private static int SIZE = 1;

    public TerminalBNFExpression () {
        this.variables = new ArrayList<AbstractMLVariable>(SIZE);
    }

    /**
     * Строит терминальное выражение по заданной металингвистической переменной.
     *
     * @param variable металингвистическая переменная
     */
    public TerminalBNFExpression (AbstractMLVariable variable) {
        this();
        addVariable(variable);
    }

    public TerminalBNFExpression (TerminalMLVariable variable) {
        this();
        this.variables.add(variable);
    }

    public List<AbstractMLVariable> getVariables() {
        return this.variables;
    }

    /**
     * Добавляет металингвистическую переменную в выражение.
     * Перед вставкой выполняется проверка перемнной на принадлежность к классу
     * терминальных переменных
     * Так как терминальное выражение может содержать только одну переменную,
     * при добавлении переменной в уже заполненное выражение старое значение
     * стирается и добавляется новове.
     *
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

    /**
     * Проверяет на равенство заданного терминала переменной данного терминального
     * выражения
     *
     * @param term указываемая для сравнения цепочка
     * @return {@code true} если {@code term} равен содержимому данного выражения,
     * {@code false} в обратном случае
     */
    public boolean equalsTerm(String term) {
        return variables.get(0).getVariable().equals(term);
    }

    @Override
    public String toString() {
        if(!variables.isEmpty()) {
            return variables.get(0).toString() + " ";
        }
        return null;
    }
}
