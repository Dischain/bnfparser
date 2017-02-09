package com.dischain.bnfparser.BaseGrammar;

import com.dischain.bnfparser.BNFContents.AbstractMLVariable;

import java.util.HashMap;
import java.util.Map;

/**
 * Представляет хранилище всех базовых правил грамматики.
 * При загрузке данного класса выполняется статическая инициализаци
 * хранилища базовыми правилами.
 *
 * <p>В число символов базовой грамматики входят {@code SP, IS, OR, QUOTE}. Так же
 * базовая грамматика может быит расширена другими символами </p>
 *
 * @see BaseGrammarContainer
 * @see BaseGrammarMLVariable
 */
class BaseGrammarContainer {

    private static HashMap<String, BaseGrammarMLVariable> container;

    static {
        container = new HashMap<String, BaseGrammarMLVariable>();
    }

    /**
     * Помещает переменную базовой грамматики в хранилище
     *
     * @param key имя переменной
     * @param variable переменная базовой грамматики
     */
    static void put(String key, BaseGrammarMLVariable variable) {
        container.put(key, variable);
    }

    /**
     * Возвращает объект {@link BaseGrammarMLVariable}, соответствующий имени некоторой
     * металингвистической переменной.
     *
     * @param var металингвистическая переменная
     * @return соответствующий данной переменной символ базовой грамматики
     */
    public static BaseGrammarMLVariable get (AbstractMLVariable var) {
        return container.get(var.getVariable());
    }

    /**
     * Проверяет, содержит ли хранилище символ базовой грамматики, соответствующий
     * имени переменной
     *
     * @param key имя металингвистической переменной
     * @return {@code true}, если в контейнере содержится переменная с таким именем,
     * {@code false} в противном случае
     */
    static boolean containsKey(String key) {
        return container.containsKey(key);
    }

    /**
     * Проверяет совпадения слова по всем возможным псевдонимам символов базовой грамматики
     * @param var слово, для которого ищутся совпадения
     * @return <code>true</code>, если такое слово является базовым символом грамматики
     */
    static boolean containsVariable(String var) {
        for (Map.Entry<String, BaseGrammarMLVariable> baseVar : container.entrySet()) {
            if(baseVar.getKey().equals(var)) return true;
            for(String  alias : baseVar.getValue().getAliases()) {
                if(var.equals(alias)) return true;
            }
        }
        return false;
    }

    /**
     * Возвращает базовую переменную грамматики <code>BaseGrammarMLVariable</code>
     * в случае, если обаружено совпадение ключа <code>key</code> с одним из
     * возможных псевдонимов базовых символов грамматики
     * @param key ключ, для которого возвращается соответствующий базовый символ
     *            грамматики
     * @return экземпляр класса <code>BaseGrammarMLVariable</code>, соответствующий
     * базовому символу грамматики с указанным ключем
     */
    static BaseGrammarMLVariable getIncludingAliases(String key) {
        for (Map.Entry<String, BaseGrammarMLVariable> baseVar : container.entrySet()) {
            if(baseVar.getKey().equals(key)) return container.get(key);
            for(String  alias : baseVar.getValue().getAliases()) {
                if(key.equals(alias)) return baseVar.getValue();
            }
        }
        return null;
    }
}
