package com.dischain.bnfparser.Util;

import com.dischain.bnfparser.BNFContents.AbstractMLVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Util {

    public static void print(HashMap<String, ArrayList<AbstractMLVariable>> rules){

        for(Map.Entry<String, ArrayList<AbstractMLVariable>> rule: rules.entrySet()){
            System.out.println("RuleName: " + rule.getKey());
            System.out.println(rule.getValue());
        }
    }

    public static void print(Map<String, ArrayList<ArrayList<AbstractMLVariable>>> expressions){

        for (String ruleName : expressions.keySet()) {
            System.out.print(ruleName + " = ");
            for (ArrayList<AbstractMLVariable> arList : expressions.get(ruleName)) {
                System.out.println(arList);
            }
        }
    }
}
