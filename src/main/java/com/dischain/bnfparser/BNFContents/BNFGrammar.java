package com.dischain.bnfparser.BNFContents;

import java.util.HashMap;
import java.util.List;

public class BNFGrammar {

    private HashMap<String, AbstractBNFRule> rules;

    public BNFGrammar(List<AbstractBNFRule> rules) {
        this.rules = new HashMap<String, AbstractBNFRule>();
        for (AbstractBNFRule rule : rules) {
            this.rules.put(rule.getRuleName(), rule);
        }
    }

    public AbstractBNFRule getRule (String ruleName) {
        return rules.get(ruleName);
    }

    public HashMap<String, AbstractBNFRule> getRules () {
        return rules;
    }
}
