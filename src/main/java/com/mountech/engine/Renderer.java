package com.mountech.engine;

import com.mountech.gfx.Image;

import java.awt.image.DataBufferInt;

public class Renderer {
    private int pW, pH; // pixel height and pixel width
    private int[] p; // pixel

    public Renderer(GameContainer gc) {
        pW = gc.getWidth();
        pH = gc.getHeight();
        p = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData(); // this will be direct access to pixel data of the image
    }

    public void clear() {
        for (int i = 0; i < p.length; i++) {
            p[i] = 0;
        }

    }

    public void setPixel(int x, int y, int value) {
        if(x < 0 || x >=pW || y < 0 || y >= pH || value == 0xffff00ff) {
            return;
        }
        p[x + y * pW] = value;
    }

    public void drawImage(Image image, int offX, int offY) {

        int newX = 0, newY = 0, newWidth = image.getW(), newHeight = image.getH();

        // Don't render code
        if(offX < -newWidth) return;
        if(offY < -newHeight) return;
        if(offX >= pW) return;
        if(offY >= pH) return;

        // Clipping image
        if(offX < 0) { newX -= offX; }
        if(offY < 0) { newY -= offY; }
        if(newWidth + offX > pW) { newWidth -= (newWidth + offX - pW); }
        if(newHeight + offY > pH) { newHeight -= (newHeight + offY - pH); }

        for (int y = newY; y < newHeight; y++) {
            for (int x = newX; x < newWidth; x++) {
                setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
            }
        }
    }
}
