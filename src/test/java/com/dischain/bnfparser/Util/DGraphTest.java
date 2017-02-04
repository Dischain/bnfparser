package com.dischain.bnfparser.Util;

import com.dischain.bnfparser.BNFContents.AbstractBNFRule;
import com.dischain.bnfparser.BNFContents.AbstractMLVariable;
import com.dischain.bnfparser.BNFContents.BNFGrammar;
import com.dischain.bnfparser.BaseGrammar.BNFGrammarReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DGraphTest {

    private DGraph graph;

    private BNFGrammar grammar;

    @Before
    public void init() {
        String path = "src/main/java/com/dischain/bnfparser/config4.sav";
        BNFGrammarReader reader = new BNFGrammarReader();
        reader.readRule (path);
        HashMap<String, ArrayList<AbstractMLVariable>> rules = reader.formRules(reader.getListOfRules());
        reader.checkNonTerms(rules);
        Map<String, ArrayList<ArrayList<AbstractMLVariable>>> expressions = reader.trimToExpresssions(rules);
        reader.checkExpressions(expressions);
        List<AbstractBNFRule> myRules = reader.createRules(expressions);

        grammar = new BNFGrammar(myRules);
        grammar.setInitialRule("RULENAME");

        graph = new DGraph(grammar);
    }

    @Test
    public void depthFirstSearchTest() throws Exception {
        graph.depthFirstSearch("self", grammar.getRule("RULENAME").getExpressions().get(0));
        Assert.assertTrue(graph.isFound());
        graph.depthFirstSearch("point", grammar.getRule("RULENAME").getExpressions().get(0));
        Assert.assertTrue(graph.isFound());
        graph.depthFirstSearch("test", grammar.getRule("RULENAME").getExpressions().get(0));
        Assert.assertFalse(graph.isFound());
        graph.depthFirstSearch("grep", grammar.getRule("RULENAME").getExpressions().get(0));
        Assert.assertFalse(graph.isFound());

        graph.depthFirstSearch("test", grammar.getRule("TEST").getExpressions().get(0));
        Assert.assertTrue(graph.isFound());
        graph.depthFirstSearch("test", grammar.getRule("TEST").getExpressions().get(1));
        Assert.assertFalse(graph.isFound());

        graph.depthFirstSearch("view", grammar.getRule("SEP").getExpressions().get(1));
        Assert.assertTrue(graph.isFound());
        graph.depthFirstSearch("view", grammar.getRule("RULENAME").getExpressions().get(1));
        Assert.assertFalse(graph.isFound());
    }
}