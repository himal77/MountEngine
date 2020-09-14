package com.mountech.game;

import com.mountech.engine.AbstractGame;
import com.mountech.engine.GameContainer;
import com.mountech.engine.Renderer;
import com.mountech.gfx.ImageTile;

public class GameManager extends AbstractGame {
    private ImageTile image;
    private float temp = 0;

    public GameManager() {
        image = new ImageTile("/test.png", 16, 16);
    }

    public void update(GameContainer gc, float dt) {
        temp += dt * 2;
        if(temp == 3) temp = 0;
    }

    public void render(GameContainer gc, Renderer r) {
        r.drawImageTile(image, gc.getInput().getMouseX() - 8, gc.getInput().getMouseY() - 16, (int) temp, 0);
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
