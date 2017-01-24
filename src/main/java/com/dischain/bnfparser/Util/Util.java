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
}
