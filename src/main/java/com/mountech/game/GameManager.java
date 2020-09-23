package com.mountech.game;

import com.mountech.audio.SoundClip;
import com.mountech.engine.AbstractGame;
import com.mountech.engine.GameContainer;
import com.mountech.engine.Renderer;
import com.mountech.gfx.Image;
import com.mountech.gfx.ImageTile;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {
    private ImageTile image;
    private Image fullImage;
    private SoundClip clip;

    private float temp = 0;

    public GameManager() {
        image = new ImageTile("/test2.png", 16, 16);
        fullImage = new Image("/test2.png");
        clip = new SoundClip("src/main/resources/audio/mayalu.wav");
        fullImage.setAlpha(true);
    }

    public void update(GameContainer gc, float dt) {

        if(gc.getInput().isKeyDown(KeyEvent.VK_A)) {
            clip.play();
        }

        temp += dt * 2;
        if(temp == 3) temp = 0;
    }

    public void render(GameContainer gc, Renderer r) {
        r.setzDepth(Integer.MAX_VALUE);
        r.drawImage(fullImage, 20, 20);
       r.drawImage(fullImage, gc.getInput().getMouseX() + 20, gc.getInput().getMouseY() + 20);

    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
