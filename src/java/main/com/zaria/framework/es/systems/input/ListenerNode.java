package com.zaria.framework.es.systems.input;


public class ListenerNode {
    public InputListener listener;
    public ListenerNode next;

    public ListenerNode(InputListener listener) {
        this.listener = listener;
    }
}
