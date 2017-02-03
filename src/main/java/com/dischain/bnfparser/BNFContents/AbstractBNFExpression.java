package com.dischain.bnfparser.BNFContents;

import java.util.List;

/**
 * Представляет базовый тип выражения грамматики и состоят из множества
 * металингвистических переменных одного типа и ограничено символом
 * <i>или</i> (<code>OR</code>) или двойным символом новой строки.
 * <p>В общем виде описывается в виде БНФ-нотации следующим образом:
 * <p><code>EXPRESSION = LIST | LIST SP OR SP EXPRESSION</code>,
 * <p>где <code>LIST</code> определяет последовательность металингвистических
 * переменных, которые могут быть как терминальными (<code>TerminalMLVariable</code>),
 * так и нетерминальными <code>NonterminalMLVariable</code>
 *
 * @see NonterminalBNFExpression
 * @see TerminalBNFExpression
 */
public abstract class AbstractBNFExpression {

    protected List<AbstractMLVariable> variables;

    /**
     * Возвращает список из металингвистических переменных этого правила
     * грамматики
     * @return variables список из металингвистических переменных этого правила
     * грамматики
     */
    public abstract List<AbstractMLVariable> getVariables();

    /**
     * Добавляет переменную в конец выражения.
     * @param variable металингвистическая переменная, которая будет добавлена
     */
    public abstract void addVariable (AbstractMLVariable variable);

    /**
     * Проверяет, является ли данное выражение терминальным, т.е. состоит ли оно
     * только из терминальных символов грамматики
     * @return true если это терминальное выражение
     * @return false если нет
     */
    public abstract boolean isTerminalExpression ();

    public abstract boolean equalsTerm(String term);
}
