package com.dischain.bnfparser.BaseGrammar;

import com.dischain.bnfparser.BNFContents.AbstractMLVariable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Базовая металингвистическая переменная грамматики, которая может
 * содержать неогранниченное количество символов-псевдонимов
 * (<code>aliases</code>)
 *
 * @see AbstractMLVariable
 * @see BaseGrammar
 */
public class BaseGrammarMLVariable extends AbstractMLVariable{

    private ArrayList<String> aliases;

    public BaseGrammarMLVariable(String generalName, String ... aliases) {
        super(generalName);
        this.aliases = new ArrayList<String>();
        this.aliases.addAll(Arrays.asList(aliases));
    }

    public boolean isTerminal() {
        return false;
    }

    public ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    public String toString() {
        String result = getVariable().toUpperCase() + " = ";
        boolean flag = true;
        for (String alias : this.aliases) {
            if (!flag) result += " | ";
            result += '"' + alias.toString() + '"';
            flag = false;
        }
        return result;
    }
}
