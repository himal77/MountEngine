package com.mountech.engine;

import java.awt.image.DataBufferInt;

public class Renderer {
    private int pW, pH; // pixel height and pixel width
    private int[] p; // pixel

    public Renderer(GameContainer gc) {
        pW = gc.getWidth();
        pH = gc.getHeight();
        p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData(); // this will be direct access to pixel data of the image
    }

    public void clear() {
        for ( int i = 0; i < p.length; i++){
            p[i] += i;
        }
    }
}
