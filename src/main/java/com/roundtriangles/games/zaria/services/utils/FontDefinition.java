package com.roundtriangles.games.zaria.services.utils;


public class FontDefinition {
    public String path;
    public int size;

    public FontDefinition(String path, int size) {
        this.path = path;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof FontDefinition) {
            FontDefinition fd = (FontDefinition)o;
            return path.equals(fd.path) && size == fd.size;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return path.hashCode() * 31 + size * 37;
    }

    @Override
    public String toString() {
        return path + "(" + size + ")";
    }
}
