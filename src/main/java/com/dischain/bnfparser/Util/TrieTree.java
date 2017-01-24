package com.dischain.bnfparser.Util;

import java.util.ArrayList;
import java.util.Collection;


public class TrieTree<Value> {

    private static int R = 256; //unicode - basement
    private TTNode<Value> root;
    private ArrayList<OnKeyDetectedListener> listeners;

    public TrieTree() {
        root = new TTNode();
        listeners = new ArrayList<OnKeyDetectedListener>();
    }

    public TrieTree(Collection<Value> collection) {
        this();

        for (Value val : collection) {
            this.put((String) val, val);
        }
    }

    private static class TTNode<Value> {

        private Value val;
        private TTNode[] next;

        private TTNode() {
            next = new TTNode[R];
        }
    }

    public static abstract class OnKeyDetectedListener<Value> {

        private Value key;

        public OnKeyDetectedListener(Value key){
            this.key = key;
        }

        public void detect(Value detectedKey) {
            if (this.key.equals(detectedKey))
                onKeyDetected();
        }

        public Value getKey () { return this.key; }

        public abstract void onKeyDetected();
    }

    public void setOnKeyDetectedListener (OnKeyDetectedListener<Value> listener) {
        this.listeners.add(listener);
        System.out.println("Слушатель назначен");
    }

    public boolean contains (String key) {
        return (this.get(key) != null) ? true : false;
    }

    public Value get (String key) {
        TTNode x = get (root, key, 0);

        if (x == null) {
            return null;
        }

        if (listeners != null && !listeners.isEmpty()) {
            for(OnKeyDetectedListener listener : listeners) {
                listener.detect((Value) x.val);
            }
        }
        return (Value) x.val;
    }

    private TTNode get (TTNode x, String key, int d) {

        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        char c = key.charAt(d);
        return get (x.next[c], key, d+1);
    }

    public void put (String key) {
        put(key, (Value)key);
    }

    public void put (String key, Value value) {
        root = put (root, key, value, 0);
    }

    private TTNode put (TTNode x, String key, Value value, int d) {
        if (x == null) {
            x = new TTNode();
        }
        if (d == key.length()) {
            x.val = value;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put (x.next[c], key, value, d+1);
        return x;
    }

    public static void main(String[] args) {
        TrieTree<String> tt = new TrieTree<String>();
        tt.put("frst");
    }
}
