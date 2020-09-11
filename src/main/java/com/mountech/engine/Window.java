package com.mountech.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {

    private JFrame frame;
    private BufferedImage image;  // this is the image stored in ram
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    public Window(GameContainer gc) {
        image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);

        canvas = new Canvas();
        Dimension dimension = new Dimension(gc.getWidth() * (int) gc.getScale(), gc.getHeight() * (int) gc.getScale());
        canvas.setPreferredSize(dimension);
        canvas.setMaximumSize(dimension);
        canvas.setMaximumSize(dimension);

        frame = new JFrame(gc.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
    }

    public void update() {

        // Drawing to buffer strategy
        graphics.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        bufferStrategy.show();
    }
}
