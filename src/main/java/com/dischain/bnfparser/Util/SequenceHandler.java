package com.dischain.bnfparser.Util;

import java.util.ArrayList;
import java.util.List;

public class SequenceHandler {

    private String sequence;

    private List<String> list;

    private int length;
    private int current;

    public SequenceHandler(String sequence) {
        this.sequence = sequence;
        length = sequence.length();
        list = new ArrayList<String>();
        current = -1;
        createListOfWords();
    }

    private void createListOfWords() {
        int d = 0;

        while (d < length) {
            String next = "";

            while (d != length) {
                if (sequence.substring(d, d + 1).equals(" ")) { d++; next = " "; break; }
                next += sequence.substring(d, d + 1);
                d++;
                if (d == length) break;
                else if (sequence.substring(d, d + 1).equals(" ")) {
                    break;
                }
            }
            list.add(next);
        }
    }

    public String getNext() {
        current++;
        return list.get(current);
    }

    public boolean containsElse() {
        return current < list.size() - 1;
    }
}
