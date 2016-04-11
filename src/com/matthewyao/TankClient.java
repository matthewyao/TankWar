package com.matthewyao;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by matthewyao on 2016/4/11.
 */
public class TankClient extends Frame{

    private int x = 50, y = 50;

    public void lauchFrame(){
        this.setLocation(400, 300);
        this.setSize(800, 600);
        this.setTitle("TankWar");
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setBackground(Color.GREEN);
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
        x += 4;
        y += 3;
    }

    private class TankThread implements Runnable{
        @Override
        public void run() {
            while (true){
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
