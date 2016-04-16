package com.matthewyao;

import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by matthewyao on 2016/4/11.
 */
public class TankClient extends Frame{

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    public static final int GAME_REFRESH_TIME = 30;

    Tank myTank = new Tank(50,50,true,this);
    Tank enemyTank = new Tank(100,100,false,this);
    List<Missile> missiles = new ArrayList<Missile>();

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
        Thread t1 = new Thread(new TankThread());
//        Thread t2 = new Thread(new MissileCleanThread());
        t1.start();
//        t2.start();
    }

    public static void main(String[] args) {
        TankClient client = new TankClient();
        client.lauchFrame();
    }

    @Override
    public void paint(Graphics g) {
        g.drawString("missiles count:"+missiles.size(),10,50);
        myTank.draw(g);
        enemyTank.draw(g);
        for(Missile missile : missiles){
            missile.hitTank(enemyTank);
            if(! missile.isLive()) missiles.remove(missile);
            else missile.draw(g);
        }
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

//    private class MissileCleanThread implements Runnable{
//        @Override
//        public void run() {
//            while (true){
//                Iterator<Missile> iterator = missiles.iterator();
//                while (iterator.hasNext()){
//                    Missile missile = iterator.next();
//                    if ( ! missile.isLive()){
//                        iterator.remove();
//                    }
//                }
//            }
//        }
//    }

    private class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }
}
