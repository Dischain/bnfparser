package com.dischain.bnfparser.BaseGrammar;

import com.dischain.bnfparser.BNFContents.TerminalMLVariable;
import org.junit.Assert;
import org.junit.Test;

public class BaseGrammarTest {

    @Test
    public void equalsByAliasesIncludingNameTest() throws Exception {
        TerminalMLVariable currentVar = new TerminalMLVariable(BaseGrammar.get("IS").getVariable());
        Assert.assertTrue(BaseGrammar.equalsByAliasesIncludingName (currentVar, BaseGrammar.OR));
    }

    @Test
    public void equalsByNameTest() throws Exception {
        TerminalMLVariable currentVar = new TerminalMLVariable(BaseGrammar.get("OR").getVariable());
        Assert.assertTrue(BaseGrammar.equalsByName(currentVar, BaseGrammar.OR));
    }

    @Test
    public void equalsByAliasesTest() throws Exception {
        TerminalMLVariable currentVar = new TerminalMLVariable("|");
        Assert.assertTrue(BaseGrammar.equalsByAliases(currentVar, BaseGrammar.OR));
    }
}