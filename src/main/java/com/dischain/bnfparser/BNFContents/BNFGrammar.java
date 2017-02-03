package com.dischain.bnfparser.BNFContents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BNFGrammar {

    private HashMap<String, AbstractBNFRule> rules;

    private Map<String, NonterminalMLVariable> nontermAlphabet;

    private AbstractBNFRule initialRule;

    public BNFGrammar (List<AbstractBNFRule> rules) {
        this.rules = new HashMap<String, AbstractBNFRule>();
        for (AbstractBNFRule rule : rules) {
            this.rules.put(rule.getRuleName(), rule);
        }
        setNonermAlphabet();
    }

    private void setNonermAlphabet() {
        nontermAlphabet = new HashMap<String, NonterminalMLVariable>();
        for (String nonTerm : rules.keySet()) {
            nontermAlphabet.put(nonTerm, new NonterminalMLVariable(nonTerm));
        }
    }

    public BNFGrammar (List<AbstractBNFRule> rules, String initial) {
        this(rules);
        initialRule = getRule(initial);
    }

    public void setInitialRule (String initial) {
        this.initialRule = getRule(initial);
    }

    public AbstractBNFRule getInitialRule () {
        return initialRule;
    }

    public AbstractBNFRule getRule (String ruleName) {
        return rules.get(ruleName);
    }

    public AbstractBNFRule getRule (AbstractMLVariable ruleName) {
        return rules.get(ruleName.getVariable());
    }

    public Map<String, NonterminalMLVariable> getNontermAlphabet () {
        return nontermAlphabet;
    }

    public AbstractMLVariable getNonterm (String name) {
        return nontermAlphabet.get(name);
    }

    public HashMap<String, AbstractBNFRule> getRules () {
        return rules;
    }
}
