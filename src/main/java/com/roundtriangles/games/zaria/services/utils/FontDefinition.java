package com.roundtriangles.games.zaria.services.utils;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;


public class FontDefinition {
    public String path;
    public FreeTypeFontParameter parameter;

    public FontDefinition(String path, int size) {
        this.path = path;

        parameter = new FreeTypeFontParameter();
        parameter.genMipMaps = true;
        parameter.minFilter = TextureFilter.MipMapLinearLinear;
        parameter.magFilter = TextureFilter.MipMapLinearLinear;
        parameter.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof FontDefinition) {
            FontDefinition fd = (FontDefinition)o;
            return path.equals(fd.path) && parameter.size == fd.parameter.size;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return path.hashCode() * 31 + parameter.size * 37;
    }

    @Override
    public String toString() {
        return path + "(" + parameter.size + ")";
    }
}
