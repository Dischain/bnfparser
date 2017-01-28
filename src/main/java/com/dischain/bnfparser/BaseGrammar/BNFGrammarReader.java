package com.dischain.bnfparser.BaseGrammar;

import com.dischain.bnfparser.BNFContents.*;
import com.dischain.bnfparser.Util.TrieTree;

import java.io.*;
import java.util.*;

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
            reader.close();
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
                if (line.equals ("")) {
                    listOfRules.add(rule);
                    rule = "";
                } else {
                    //System.out.println(line);
                    rule += line;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверяет слово, считанное из ввода, на совпадение с символом базовой грамматики.
     * Если совпадение обнаружено, то помещает соответствующий нетерминальный символ
     * в <code>righthandside</code>. Если совпадений не обнаружено, то создается простая
     * нетерминальная переменная.
     *
     * В данном методе вырезается символ <code>IS</code> базовой грамматики.
     * @param var слово, которое подлежит проверке
     * @param righthandside правая часть выражения грамматики
     */
    public void checkValue(String var, ArrayList<AbstractMLVariable> righthandside) {
        if(BaseGrammar.equalsByAliases(var, BaseGrammar.IS)) return;
        else if(BaseGrammar.containsValue(var)) {
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
                                + " обнаружено в правиле для " + ruleName);
                        rules.remove(ruleName);
                    }
                    //throw new NonTerminalSymbolsDefinitionEexception (variable.getValue());
                }
            }
        } return true;
    }

    /**
     * Разбивает множество пар (имя правила - список металингвистических переменных), (в которых
     * уже отсутствует нетерминал <code>IS</code>) на отображение множества имен правил на множество
     * выражений. В отдельное выражение попадает множество символов, разделенных базовыми
     * терминалами грамматики <code>OR</code>
     */
    public Map<String, ArrayList<ArrayList<AbstractMLVariable>>> trimToExpresssions
    (Map<String, ArrayList<AbstractMLVariable>> rules) {
        Map<String, ArrayList<ArrayList<AbstractMLVariable>>> ruleMap =
                new HashMap<String, ArrayList<ArrayList<AbstractMLVariable>>>();

        for (String ruleName : rules.keySet()) {
            int d = 0;
            int ruleLength = rules.get(ruleName).size();
            ArrayList<ArrayList<AbstractMLVariable>> exprList = new ArrayList<ArrayList<AbstractMLVariable>>();

            while (d < ruleLength) {
                ArrayList<AbstractMLVariable> expr = new ArrayList<AbstractMLVariable>();

                while (d != ruleLength) {
                    AbstractMLVariable currentVar = rules.get(ruleName).get(d);
                    if (!BaseGrammar.equalsByName (currentVar, BaseGrammar.OR)) {
                        expr.add(currentVar);
                        d ++;
                        if (d + 1 > ruleLength) {
                            exprList.add(expr);
                            break;
                        }
                    } else {
                        exprList.add(expr);
                        d++;
                        break;
                    }
                }
            }
            ruleMap.put(ruleName, exprList);
            System.out.println(exprList);
        }
        return ruleMap;
    }

    /**
     * Выполняет проверку на наличие терминальных и нетерминальных символов в однов выражениее
     * @param exprMap
     */
    public void checkExpressions (Map<String, ArrayList<ArrayList<AbstractMLVariable>>> exprMap) {
        for(String ruleName : exprMap.keySet()) {
            for (ArrayList<AbstractMLVariable> expression: exprMap.get(ruleName)) {
                int checkSum1 = expression.size();
                int checkSum2 = 0;
                for (int i = 0; i < expression.size(); i++) {
                    if (!expression.get(i).isTerminal())
                        checkSum2++;
                    else if (expression.get(i).isTerminal())
                        checkSum2--;
                    //else checkSum2--;
                }
                if(checkSum1 != Math.abs(checkSum2)) {
                    System.out.println("В правиле " + ruleName + " присутствуют терминальыне и нетерминальные символы");
                }
                //System.out.println("Для правила: " + ruleName + " checkSum1: " + checkSum1 + " checkSum2: " + checkSum2);
            }
        }
    }

    /**
     * Выполняет проверку на то, что в последовательности выражений в правиле все выражение одного типа
     */
    public void checkExpressionsSequence (ArrayList<AbstractBNFExpression>  expressions) {

    }

    public List<AbstractBNFRule> createRules (Map <String, ArrayList<ArrayList<AbstractMLVariable>>> rulesMap) {
        ArrayList<AbstractBNFRule> rules = new ArrayList<AbstractBNFRule>();

        for (String ruleName : rulesMap.keySet()) {
            ArrayList<AbstractBNFExpression> expressions = this.createExpressions(rulesMap.get(ruleName));

            //Если первое выражение терминальное, то и все выражения - терминальные. И наоборот.
            if (expressions.get(0).isTerminalExpression()) {
                rules.add (new TerminalBNFRule (ruleName, expressions));
            } else {
                rules.add (new NonterminalBNFRule (ruleName, expressions));
            }
        }
        return rules;
    }

    public ArrayList<AbstractBNFExpression> createExpressions (ArrayList<ArrayList<AbstractMLVariable>> vars) {
        ArrayList<AbstractBNFExpression> expressions = new ArrayList<AbstractBNFExpression>();

        for (ArrayList<AbstractMLVariable> list : vars) {
            AbstractBNFExpression expr;
            //Если первая переменная терминальная, то все переменные - терминальные. И наоборот.
            if (list.get(0).isTerminal()) {
                expr = new TerminalBNFExpression (list.get(0));
            } else {
                expr = new NonterminalBNFExpression (list);
            }
            expressions.add(expr);
        }
        return expressions;
    }

    public static void main(String[] args) {
        String path = "src/main/java/com/dischain/bnfparser/config2.sav";
        BNFGrammarReader reader = new BNFGrammarReader();
        reader.readRule (path);
        HashMap<String, ArrayList<AbstractMLVariable>> rules = reader.formRules(reader.getListOfRules());
        reader.checkNonTerms(rules);
        Map<String, ArrayList<ArrayList<AbstractMLVariable>>> expressions = reader.trimToExpresssions(rules);
        reader.checkExpressions(expressions);
        // TODO: 28.01.2017 Test "createRules" and "createExpressions" methodes.
        //Util.print(expressions);
    }
}
