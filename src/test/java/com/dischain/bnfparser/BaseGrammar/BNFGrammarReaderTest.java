package com.dischain.bnfparser.BaseGrammar;

import com.dischain.bnfparser.BNFContents.AbstractBNFRule;
import com.dischain.bnfparser.BNFContents.AbstractMLVariable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BNFGrammarReaderTest {

    private String path = "src/main/java/com/dischain/bnfparser/config3.sav";
    private BNFGrammarReader reader;

    @Before
    public void init() {
        reader = new BNFGrammarReader();
        reader.readRule(path);
    }

    @Test
    public void getListOfRulesTest() {
        ArrayList<String> listOfRules = reader.getListOfRules();
        System.out.println(listOfRules);
    }

    @Test
    public void formRulesTest() {
        ArrayList<String> listOfRules = reader.getListOfRules();
        HashMap<String, ArrayList<AbstractMLVariable>> rules = reader.formRules(listOfRules);
        System.out.println(rules);
    }

    @Test
    public void createRulesTest() {
        /*HashMap<String, ArrayList<AbstractMLVariable>> rules = reader.formRules(reader.getListOfRules());
        reader.checkNonTerms(rules);
        Map<String, ArrayList<ArrayList<AbstractMLVariable>>> expressions = reader.trimToExpresssions(rules);
        reader.checkExpressions(expressions);
        List<AbstractBNFRule> myRules = reader.createRules(expressions);*/
    }


}