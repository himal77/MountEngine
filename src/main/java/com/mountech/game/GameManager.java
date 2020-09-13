package com.mountech.game;

import com.mountech.engine.AbstractGame;
import com.mountech.engine.GameContainer;
import com.mountech.engine.Renderer;
import com.mountech.gfx.Image;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {
    private Image image;

    public GameManager() {
        image = new Image("/test.png");
    }

    public void update(GameContainer gc, float dt) {

    }

    public void render(GameContainer gc, Renderer r) {
        r.drawImage(image, gc.getInput().getMouseX(), gc.getInput().getMouseY());
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
