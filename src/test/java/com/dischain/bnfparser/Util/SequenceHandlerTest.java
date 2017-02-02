package com.dischain.bnfparser.Util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SequenceHandlerTest {

    String sequence;
    SequenceHandler handler;

    @Before
    public void init() {
        sequence = " some sequence with spaces";
        handler = new SequenceHandler(sequence);
    }

    @Test
    public void getNextTest() throws Exception {
        Assert.assertTrue(handler.getNext().equals(" "));
        Assert.assertTrue(handler.getNext().equals("some"));
        Assert.assertTrue(handler.getNext().equals(" "));
        Assert.assertTrue(handler.getNext().equals("sequence"));
        Assert.assertTrue(handler.getNext().equals(" "));
        Assert.assertTrue(handler.getNext().equals("with"));
        Assert.assertTrue(handler.getNext().equals(" "));
        Assert.assertTrue(handler.getNext().equals("spaces"));
    }
}