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
}
