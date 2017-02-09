package com.dischain.bnfparser.BNFContents;

import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс для единицы БНФ-грамматики, представляет правило грамматики.
 * <p>Общая форма правила грамматики должна быть записана в следующей форме:
 * <p><code>BNFRULE = RULENAME IS EXPRESSION</code>,
 * <p>где нетерминальный символ <code>IS</code> эквивалентен терминальному
 * символу <code>"="</code>
 *
 * <p>Правило грамматики определяется множеством <i>выраженией</i>
 * {@link AbstractBNFExpression} в его правой части и
 * реализующим правило <code>RULE</code> базовой грамматики, которое
 * в общей форме записывается в виде следющего рекурсивного определения:
 * <p><code>RULE = LIST | LIST SP OR SP EXPRESSION</code>
 * <p><code>LIST = TERM | TERM SP LIST</code>
 *
 * <p><code>LIST</code> определяет последовательность металингвистических
 * переменных, которые могут быть как терминальными ({@link TerminalMLVariable}),
 * так и нетерминальными {@link NonterminalMLVariable}
 *
 * <p>Для удобства разработки в качестве реализации правила грамматики используются
 * два типа классов:
 * <p>{@link NonterminalBNFRule} - представляет правило, описывающее множество
 * нетерминальных выражений, т.е. выражений, сотоящих только из нетерминвльных
 * символов.
 * <p>{@link TerminalBNFRule} - представляет правило, описывающее множество
 * терминальных выражений, т.е. состоящих только из терминальных символов.
 * <p>При наличии в правой части правила последовательности, разделенной
 * символом <i>или</i> (<code>OR</code>) возникает ситуация, в которой мы имеем
 * несколько выражений определенного типа в одном правиле.
 * <p>Например, в рассмотренном выше правиле <code>RULE</code> имеем два
 * нетерминальных выражения, состоящих из <code>LIST</code> и
 * <code>LIST SP OR SP EXPRESSION</code> соответственно. Тогда правило, сотоящее
 * из таких выражений, будет называться нетерминальным.
 *
 * @see NonterminalBNFRule
 * @see TerminalBNFRule
 */
public abstract class AbstractBNFRule {

    protected String ruleName;

    protected List<AbstractBNFExpression>  expressions;

    public AbstractBNFRule () {
        ruleName = "";
        expressions = new ArrayList<AbstractBNFExpression>();
    }

    public AbstractBNFRule (String ruleName) {
        this.ruleName = ruleName;
        expressions = new ArrayList<AbstractBNFExpression>();
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleName () {
        return ruleName;
    }

    /**
     * Возвращает список выражений, описывающих данное правило.
     * @return список выражений
     */
    public List<AbstractBNFExpression> getExpressions () {
        return expressions;
    }

    public abstract void addExpressions(AbstractBNFExpression ... expressions);

    public abstract boolean isTerminalRule ();

    public abstract boolean containsTerm (String term);

    public abstract List <List <AbstractMLVariable>> getListsOfVariables ();
}
