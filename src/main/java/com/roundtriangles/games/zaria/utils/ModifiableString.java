package com.roundtriangles.games.zaria.utils;


public class ModifiableString {

    StringBuilder builder;

    public ModifiableString(int capacity) {
        builder = new StringBuilder(capacity);
    }

    public void setChar(int index, char character) {
        if (builder.length() <= index) {
            builder.setLength(index + 1);
        }
        builder.setCharAt(index, character);
    }

    public void reset() {
        builder.setLength(0);
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
