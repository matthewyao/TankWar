package com.matthewyao;

import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by matthewyao on 2016/4/11.
 */
public class TankClient extends Frame{

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    public static final int GAME_REFRESH_TIME = 30;

    private int x = 50, y = 50;
    private int inc = 5;

    private Image offScreenImage = null;

    public void lauchFrame(){
        this.setLocation(400, 300);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setTitle("TankWar");
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setBackground(Color.GREEN);
        this.addKeyListener(new KeyMonitor());
        this.setVisible(true);
        new TankThread().run();
    }

    public static void main(String[] args) {
        TankClient client = new TankClient();
        client.lauchFrame();
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, 30, 30);
        g.setColor(c);
    }

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null){
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gImage = offScreenImage.getGraphics();
        Color c = gImage.getColor();
        gImage.setColor(Color.GREEN);
        gImage.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gImage.setColor(c);
        paint(gImage);
        g.drawImage(offScreenImage,0,0,null);
    }

    private class TankThread implements Runnable{
        @Override
        public void run() {
            while (true){
                repaint();
                try {
                    Thread.sleep(GAME_REFRESH_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_UP:y -= inc;break;
                case KeyEvent.VK_RIGHT:x += inc;break;
                case KeyEvent.VK_DOWN:y += inc;break;
                case KeyEvent.VK_LEFT:x -= inc;break;
            }
        }
    }
}
