package com.zaria.framework;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class TexturePackerMain {

    public static void main(String[] args) {
        String inputDir = args[0];
        String outputDir = args[1];
        String packFileName = args[2];

        TexturePacker2.process(inputDir, outputDir, packFileName);
    }

}
