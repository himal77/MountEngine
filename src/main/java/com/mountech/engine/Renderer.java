package com.mountech.engine;

import com.mountech.gfx.Font;
import com.mountech.gfx.Image;
import com.mountech.gfx.ImageTile;

import java.awt.image.DataBufferInt;

public class Renderer {
    private int pW, pH; // pixel height and pixel width
    private int[] p; // pixel
    private int[] zb; //zBuffer

    private int zDepth = 0;

    private Font font = Font.STANDARD;

    public Renderer(GameContainer gc) {
        pW = gc.getWidth();
        pH = gc.getHeight();
        p = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData(); // this will be direct access to pixel data of the image
        zb = new int[p.length];
    }

    public void clear() {
        for (int i = 0; i < p.length; i++) {
            p[i] = 0;
            zb[i] = 0;
        }
    }

    public void setPixel(int x, int y, int value) {
        int alpha = (value >> 24) & 0xff;
        if (x < 0 || x >= pW || y < 0 || y >= pH || alpha == 0) {
            return;
        }
        if (zb[x + y * pW] > zDepth) return;

        if (alpha == 255) {
            p[x + y * pW] = value;
        } else {
            int pixelColor = p[x + y * pW];

            int newRed = ((pixelColor >> 16) & 0xff) - (int) ((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * alpha / 255f);
            int newGreen = ((pixelColor >> 8) & 0xff) - (int) ((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * alpha / 255f);
            int newBlue = (pixelColor & 0xff) - (int) ((pixelColor & 0xff) - (value & 0xff) * alpha / 255f);


            p[x + y * pW] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
        }
    }

    public void drawText(String text, int offX, int offY, int color) {
        text = text.toUpperCase();
        int offset = 0;

        for (int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i) - 32;

            for (int y = 0; y < font.getFontImage().getH(); y++) {
                for (int x = 0; x < font.getWidths()[unicode]; x++) {
                    if (font.getFontImage().getP()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getW()] == 0xffffffff) {
                        setPixel(x + offX + offset, y + offY, color);
                    }
                }
            }

            offset += font.getWidths()[unicode];
        }
    }

    public void drawImage(Image image, int offX, int offY) {
        // Don't render code
        if (offX < -image.getW()) return;
        if (offY < -image.getH()) return;
        if (offX >= pW) return;
        if (offY >= pH) return;

        int newX = 0, newY = 0, newWidth = image.getW(), newHeight = image.getH();

        // Clipping image
        if (offX < 0) {
            newX -= offX;
        }
        if (offY < 0) {
            newY -= offY;
        }
        if (newWidth + offX > pW) {
            newWidth -= (newWidth + offX - pW);
        }
        if (newHeight + offY > pH) {
            newHeight -= (newHeight + offY - pH);
        }

        for (int y = newY; y < newHeight; y++) {
            for (int x = newX; x < newWidth; x++) {
                setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
        // Don't render code
        if (offX < -image.getTileW()) return;
        if (offY < -image.getTileH()) return;
        if (offX >= pH) return;
        if (offY >= pW) return;

        int newX = 0, newY = 0, newWidth = image.getTileW(), newHeight = image.getTileH();

        // Clipping image
        if (offX < 0) {
            newX -= offX;
        }
        if (offY < 0) {
            newY -= offY;
        }
        if (newWidth + offX > pW) {
            newWidth -= (newWidth + offX - pW);
        }
        if (newHeight + offY > pH) {
            newHeight -= (newHeight + offY - pH);
        }

        for (int y = newY; y < newHeight; y++) {
            for (int x = newX; x < newWidth; x++) {
                setPixel(x + offX, y + offY,
                        image.getP()[(x + tileX * image.getTileW()) +
                                (y + tileY * image.getTileH()) * image.getW()]);
            }
        }
    }

    public void drawRect(int offX, int offY, int width, int height, int color) {   // Don't render code
        if (offX < -width) return;
        if (offY < -height) return;
        if (offX >= pH) return;
        if (offY >= pW) return;

        int newX = 0, newY = 0, newWidth = width, newHeight = height;

        // Clipping image
        if (offX < 0) {
            newX -= offX;
        }
        if (offY < 0) {
            newY -= offY;
        }
        if (newWidth + offX > pW) {
            newWidth -= (newWidth + offX - pW);
        }
        if (newHeight + offY > pH) {
            newHeight -= (newHeight + offY - pH);
        }


        for (int y = newY; y <= newHeight; y++) {
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
        }

        for (int x = newX; x <= newWidth; x++) {
            setPixel(x + offX, offY, color);
            setPixel(x + offX, offY + height, color);
        }
    }

    public void fillRect(int offX, int offY, int width, int height, int color) {
        if (offX < -width) return;
        if (offY < -height) return;
        if (offX >= pH) return;
        if (offY >= pW) return;

        int newX = 0, newY = 0, newWidth = width, newHeight = height;

        // Clipping image
        if (offX < 0) {
            newX -= offX;
        }
        if (offY < 0) {
            newY -= offY;
        }
        if (newWidth + offX > pW) {
            newWidth -= (newWidth + offX - pW);
        }
        if (newHeight + offY > pH) {
            newHeight -= (newHeight + offY - pH);
        }

        for (int y = newY; y <= newHeight; y++) {
            for (int x = newX; x <= newWidth; x++) {
                setPixel(x + offX, y + offY, color);
            }
        }
    }

    public void setzDepth(int zDepth) {
        this.zDepth = zDepth;
    }
}
