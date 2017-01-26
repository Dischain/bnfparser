package com.dischain.bnfparser.BaseGrammar;

import com.dischain.bnfparser.BNFContents.AbstractMLVariable;

/**
 * Представляет набор базовых правил грамматики, на основании которых могут быть
 * построены исходные описания грамматики.
 * <p>Каждое правило содержит несколько псевдонимов и при необходимости может быть
 * расширено.
 * <p>Если правило содержится в хранилище <code>BaseGrammarContainer</code>, то в описании
 * грамматики используемого языка его учет необязательный.
 */
public class BaseGrammar {

    /**
     * Equals to SP = " " | "\t" terminal rule
     */
    public static final BaseGrammarMLVariable SP = new BaseGrammarMLVariable("SP", " ", "\t");

    /**
     * Equals to OR = "|" terminal rule
     */
    public static final BaseGrammarMLVariable OR = new BaseGrammarMLVariable("OR", "|");

    /**
     * Equals to IS = "=" terminal rule
     */
    public static final BaseGrammarMLVariable IS = new BaseGrammarMLVariable("IS", "=");

    /**
     * Equals to QUOTE = '"' terminal rule
     */
    public static final BaseGrammarMLVariable QUOTE = new BaseGrammarMLVariable("QUOTE", String.valueOf('"'));

    static {
        BaseGrammarContainer.put(SP.getVariable(), SP);
        BaseGrammarContainer.put(OR.getVariable(), OR);
        BaseGrammarContainer.put(IS.getVariable(), IS);
        BaseGrammarContainer.put(QUOTE.getVariable(), QUOTE);
    }

    public static boolean containsValue (String var) {
        return BaseGrammarContainer.containsVariable(var);
    }

    /**
     * Проверяет, является ли <code>symbol</code> базовым символом грамматики по всем
     * возможным псевдонимам
     * @param symbol металингвистическая переменная, которая подлежит проверке
     * @return <code>true</code>, если является базовым символом грамматики,
     * <code>false</code>, в противном случае.
     */
    public static boolean isBaseGrammarSymbol (AbstractMLVariable symbol) {
        return BaseGrammarContainer.containsKey(symbol.getVariable());
    }

    /**
     * Возвращает металингвистическую переменную, соответствующую некоторой 
     * базовой переменной грамматики или любому из ее псевдонимов
     * @param key
     * @return
     */
    public static AbstractMLVariable get(String key){
        return BaseGrammarContainer.getIncludingAliases(key);
    }

    public static boolean equals(String  val1, BaseGrammarMLVariable val2) {
        for (String alias : val2.getAliases()) {
            if (alias.equalsIgnoreCase(val1))
                return true;
        }
        return false;
    }

    /**
     * Выполняется сравнение указанной переменной и символа базовой грамматики.
     * Условие истинно <b>только в случае совпадения имен</b>, т.е. если дана
     * переменная:
     * <p><code>NonterminalMLVariable var = new NonterminalMLVariable("IS")</code>
     * <p>то проверка типа
     * <p><code>BaseGrammar.equalsByName(var, BaseGrammar.IS)</code>
     * <p>вернет <code>true</code>, но та же провeрка, только при инициализации
     * переменной <code>var = new NonterminalMLVariable("=")</code>
     * вернет <code>false</code>, т.к. теперь имя переменной <code>var</code> есть
     * <code>'='</code>
     * @param var переменная, чье <b>имя</b> будет сравниваться с символом базовой грамматики
     * @param bvar переменная базовой грамматики
     * @return <code>true</code>, если обе переменные <b>равны по имени</b>
     */
    public static boolean equalsByName (AbstractMLVariable var, BaseGrammarMLVariable bvar) {
        return var.getVariable().equalsIgnoreCase(bvar.getVariable());
    }

    /**
     * Выполняется сравнение указанного имени переменной и символа базовой грамматики.
     * Условие истинно <b>только в случае совпадения имен</b>, т.е. если дана
     * переменная:
     * <p><code>String var = "IS"</code>
     * <p>то проверка типа
     * <p><code>BaseGrammar.equalsByName(var, BaseGrammar.IS)</code>
     * <p>вернет <code>true</code>, но та же провeрка, только при инициализации
     * переменной <code>var = "="</code>
     * вернет <code>false</code>, т.к. теперь имя переменной <code>var</code> есть
     * <code>'='</code>
     * @param var имя переменной
     * @param bvar переменная базовой грамматики
     * @return <code>true</code>, если обе переменные <b>равны по имени</b>
     */
    public static boolean equalsByName (String var, BaseGrammarMLVariable bvar) {
        return var.equalsIgnoreCase(bvar.getVariable());
    }

    /**
     * Выполняется сравнение указанной переменной со всеми псевдонимами указанного
     * символа базовой грамматики. Условие может быть истинно только в случае
     * совпадания имени переменной с одним из <b>псевдонимов  символа базовой грамматики</b>.
     * Например, если дана переменная
     * <p>NonterminalMLVariable var = new NonterminalMLVariable("IS"),
     * <p>то проверка типа
     * <p><code>BaseGrammar.equalsBuAliases(var, BaseGrammar.IS)</code>
     * вернет <code>false</code>, но если инициализировать переменную <code>var</code> так:
     * <p>NonterminalMLVariable var = new NonterminalMLVariable("="),
     * <p>то та же проверка вернет <code>true</code>, т.к. <code>"="</code> является псевдонимом
     * символа базовой грамматики и именем нашей переменной.
     * @param var переменная, чье <b>имя</b> будет сравниваться с псевдонимами символа
     * базовой грамматики
     * @param bvar переменная базовой грамматики
     * @return <code>true</code>, если зотябы один <b>псевдоним равен имени переменной</b>
     */
    public static boolean equalsByAliases (AbstractMLVariable var, BaseGrammarMLVariable bvar) {
        for (String alias : bvar.getAliases()) {
            if(var.getVariable().equals(alias))
                return true;
            else continue;
        }
        return false;
    }

    /**
     * Выполняется сравнение указанного имени переменной со всеми псевдонимами указанного
     * символа базовой грамматики. Условие может быть истинно только в случае
     * совпадания имени переменной с одним из <b>псевдонимов  символа базовой грамматики</b>.
     * Например, если дана переменная
     * <p>String var = "IS",
     * <p>то проверка типа
     * <p><code>BaseGrammar.equalsBuAliases(var, BaseGrammar.IS)</code>
     * вернет <code>false</code>, но если инициализировать переменную <code>var</code> так:
     * <p>String var = "=",
     * <p>то та же проверка вернет <code>true</code>, т.к. <code>"="</code> является псевдонимом
     * символа базовой грамматики и именем нашей переменной.
     * @param var имя переменной
     * @param bvar переменная базовой грамматики
     * @return <code>true</code>, если зотябы один <b>псевдоним равен имени переменной</b>
     */
    public static boolean equalsByAliases (String var, BaseGrammarMLVariable bvar) {
        for (String alias : bvar.getAliases()) {
            if(var.equals(alias))
                return true;
        }
        return false;
    }

    public static boolean equalsByAliasesIncludingName (AbstractMLVariable var, BaseGrammarMLVariable bvar) {
        return equalsByName(var, bvar) || equalsByAliases(var, bvar);
    }


    public static boolean equalsByAliasesIncludingName (String var, BaseGrammarMLVariable bvar) {
        return equalsByName(var, bvar) || equalsByAliases(var, bvar);
    }
}
