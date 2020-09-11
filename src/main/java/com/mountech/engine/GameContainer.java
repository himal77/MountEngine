package com.mountech.engine;

public class GameContainer implements Runnable {

    private Thread thread;
    private Window window;

    private boolean running = false;
    private final double UPDATE_CAP = 1.0 / 60.0;
    private int width = 320, height = 240;
    private float scale = 4f;
    private String title = "MountEngine v1.0";

    public GameContainer() {

    }

    public void start() {
        window = new Window(this);

        thread = new Thread(this);
        thread.run();  // .start() will make separate thread, this will it main thread
    }

    public void stop() {

    }

    public void run() {
        running = true;

        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;

        while (running) {
            render = false;
            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;
            unprocessedTime += passedTime;

            while (unprocessedTime >= UPDATE_CAP) {
                unprocessedTime -= UPDATE_CAP;
                render = true;
                // TODO: update game
            }

            if (render) {
                // TODO: Render game
                window.update();
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        dispose();
    }

    private void dispose() {

    }

    public static void main(String[] args) {
        GameContainer gameContainer = new GameContainer();
        gameContainer.start();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getScale() {
        return scale;
    }

    public String getTitle() {
        return title;
    }
}
