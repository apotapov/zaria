package com.zaria.framework;


public class AtlasGenerator {

    public static void main(String[] args) {
        /*
         * green_tile_border
  rotate: false
  xy: 2, 2
  size: 64, 64
  orig: 64, 64
  offset: 0, 0
  index: -1
         */
        int x = 2;
        int y = 2;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println("explosion");
                System.out.println("  rotate: false");
                System.out.println("  xy: " + x + ", " + y);
                System.out.println("  size: 64, 64");
                System.out.println("  orig: 64, 64");
                System.out.println("  offset: 0, 0");
                System.out.println("  index: " + (i * 5 + j));
                x += 64;
            }
            y += 64;
            x = 2;
        }

    }

}
