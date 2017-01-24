package com.dischain.bnfparser.BaseGrammar;

import com.dischain.bnfparser.BNFContents.AbstractMLVariable;
import com.dischain.bnfparser.BNFContents.NonterminalMLVariable;
import com.dischain.bnfparser.BNFContents.TerminalMLVariable;
import com.dischain.bnfparser.Util.TrieTree;
import com.dischain.bnfparser.Util.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class BNFGrammarReader {

    private ArrayList<String> listOfRules;
    BufferedReader reader;

    public BNFGrammarReader () {
        listOfRules = new ArrayList<String>();
    }

    public ArrayList<String> getListOfRules () {
        return listOfRules;
    }

    public void readRule (InputStream in) {
        String rule = new String();
        String line;

        try {
            reader = new BufferedReader(new InputStreamReader(in));

            while ((line = reader.readLine()) != null) {
                if (line.equals("\n")) {
                    listOfRules.add(rule);
                    rule = "";
                } else {
                    rule += line;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readRule (String path) {

        String rule = new String ();
        String line;

        try {
            reader = new BufferedReader (new FileReader (path));

            while ((line = reader.readLine()) != null) {
                if (line.equals ("")) { // тут проверить гипотезу, что readline() читает поток, пока                                                       // не встретит символ новой строки
                    listOfRules.add(rule);
                    rule = "";
                } else {
                    //System.out.println(line);
                    rule += line;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверяет слово, считанное из ввода, на совпадение с символом баловой грамматики.
     * Если совпадение обнаружено, то помещает соответствующий нетерминальный символ
     * в <code>righthandside</code>. Если совпадений не обнаружено, то создается простая
     * нетерминальная переменнаяю
     * @param var слово, которое подлежит проверке
     * @param righthandside правая часть выражения грамматики
     */
    public static void checkValue(String var, ArrayList<AbstractMLVariable> righthandside) {
        if(var.equals("SP")) return;
        if(BaseGrammar.containsValue(var)) {
            String name = BaseGrammar.get(var).getVariable();
            NonterminalMLVariable ntmlv = new NonterminalMLVariable(name);
            righthandside.add(ntmlv);
        } else {
            righthandside.add(new NonterminalMLVariable(var));
        }
    }

    public HashMap<String, ArrayList<AbstractMLVariable>> formRules(ArrayList<String> ruleSet) {
        HashMap<String, ArrayList<AbstractMLVariable>> rules =
                new HashMap<String, ArrayList<AbstractMLVariable>>();

        for (String str : ruleSet) {
            ArrayList <AbstractMLVariable> righthandside  = new ArrayList<AbstractMLVariable>();
            String ruleName = "";

            String rule = str.trim();

            int d = 0;
            while (d < rule.length()) {
                String var = "";

                if (rule.substring(d, d + 1).equals(" ")
                        || rule.substring(d, d + 1).equals("\t")) { d++; continue; }

                while (d != rule.length()) {
                    if (rule.substring(d, d + 1).equals(" ")) { d++; continue; }
                    var += rule.substring(d, d + 1);
                    d++;
                    if (d == rule.length()) break;
                    else if (rule.substring(d, d + 1).equals(" ")
                            || rule.substring(d, d + 1).equals("\t")) {d++; break; }
                }
                if (rule.startsWith (var) && ruleName.equals("")) {
                    ruleName = var;
                } else if (var.startsWith(String.valueOf('"')) && var.endsWith(String.valueOf('"')) ){
                    righthandside.add (new TerminalMLVariable(var.substring(1, var.length() - 1)));
                } else checkValue(var, righthandside);
            }

            rules.put(ruleName, righthandside);
        }
        return rules;
    }

    /**
     * Проверяет наличие правил вывода для каждого из используемых нетерминалов
     * грамматики
     * @param rules множество всех правил, обработанных после чтения из файла
     * @return <code>true</code> , если все нетерминалы имеют соответствующее
     * правило вывода
     */
    public boolean checkNonTerms (HashMap <String, ArrayList<AbstractMLVariable>> rules){
        Collection<String> ruleNames = rules.keySet();
        TrieTree tt = new TrieTree (ruleNames);

        for (String ruleName : ruleNames) {
            for (AbstractMLVariable variable : rules.get(ruleName)) {
                if (!variable.isTerminal()) {
                    if (!tt.contains (variable.getVariable()) &&
                            !BaseGrammar.isBaseGrammarSymbol(variable)) {
                        System.out.println("Отсутствует определение правила " + variable.getVariable()
                                + "обнаружено в правиле для " + ruleName);
                        rules.remove(ruleName);
                    }
                    //throw new NonTerminalSymbolsDefinitionEexception (variable.getValue());
                }
            }
        } return true;
    }


    public static void main(String[] args) {
        String path = "src/main/java/com/dischain/bnfparser/config.sav";
        BNFGrammarReader reader = new BNFGrammarReader();
        reader.readRule (path);
        HashMap<String, ArrayList<AbstractMLVariable>> rules = reader.formRules(reader.getListOfRules());
        reader.checkNonTerms(rules);
        Util.print(rules);
    }
}
