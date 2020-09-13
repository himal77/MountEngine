package com.mountech.game;

import com.mountech.engine.AbstractGame;
import com.mountech.engine.GameContainer;
import com.mountech.engine.Renderer;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    public GameManager() {

    }

    public void update(GameContainer gc, float dt) {
        if(gc.getInput().isKeyDown(KeyEvent.VK_A)) {
            System.out.println("A was pressed");
        }
    }

    public void render(GameContainer gc, Renderer r) {

    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
