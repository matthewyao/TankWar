package com.matthewyao;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by matthewyao on 2016/4/11.
 */
public class Tank {
    private int x,y;
    private int X_SPEED = 15,Y_SPEED = 15;
    public static final int WIDTH = 30,HEIGHT = 30;
    private Direction dir,ptDir;
    TankClient tankClient;

    private boolean bu = false, br = false, bd = false, bl = false;
    public enum Direction{U,RU,R,RD,D,LD,L,LU,STOP}

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
        dir = Direction.STOP;
        ptDir = Direction.U;
    }

    public Tank(int x, int y, TankClient tankClient) {
        this(x,y);
        this.tankClient = tankClient;
    }

    public void drawPt(Graphics g){
        switch (ptDir){
            case U:g.drawLine(x + WIDTH/2,y + HEIGHT/2,x + WIDTH/2,y);break;
            case RU:g.drawLine(x + WIDTH/2,y + HEIGHT/2,x + WIDTH,y);break;
            case R:g.drawLine(x + WIDTH/2,y + HEIGHT/2,x + WIDTH,y + HEIGHT/2);break;
            case RD:g.drawLine(x + WIDTH/2,y + HEIGHT/2,x + WIDTH,y + HEIGHT);break;
            case D:g.drawLine(x + WIDTH/2,y + HEIGHT/2,x + WIDTH/2,y + HEIGHT);break;
            case LD:g.drawLine(x + WIDTH/2,y + HEIGHT/2,x ,y + HEIGHT);break;
            case L:g.drawLine(x + WIDTH/2,y + HEIGHT/2,x,y + HEIGHT/2);break;
            case LU:g.drawLine(x + WIDTH/2,y + HEIGHT/2,x,y);break;
        }
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(Color.black);
        drawPt(g);
        g.setColor(c);
        move();
    }

    public void move(){
        switch (dir){
            case U:y -= Y_SPEED;break;
            case RU:x += X_SPEED;y -= Y_SPEED;break;
            case R:x += X_SPEED;break;
            case RD:x += X_SPEED;y += Y_SPEED;break;
            case D:y += Y_SPEED;break;
            case LD:x -= X_SPEED;y += Y_SPEED;break;
            case L:x -= X_SPEED;break;
            case LU:x -= X_SPEED;y -= Y_SPEED;break;
            case STOP:break;
        }
        if (dir != Direction.STOP) ptDir = dir;
        //·ÀÖ¹Ì¹¿Ë³ö½ç
        if (x < 4) x = 4;
        if (y < 27) y = 27;
        if (x > TankClient.GAME_WIDTH - Tank.WIDTH - 5) x = TankClient.GAME_WIDTH - Tank.WIDTH - 5;
        if (y > TankClient.GAME_HEIGHT - Tank.HEIGHT - 5) y = TankClient.GAME_HEIGHT - Tank.HEIGHT - 5;

    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_UP:bu = true;break;
            case KeyEvent.VK_RIGHT:br = true;break;
            case KeyEvent.VK_DOWN:bd = true;break;
            case KeyEvent.VK_LEFT:bl = true;break;
        }
        calcDirection();
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_CONTROL:
                fire();break;
            case KeyEvent.VK_UP:bu = false;break;
            case KeyEvent.VK_RIGHT:br = false;break;
            case KeyEvent.VK_DOWN:bd = false;break;
            case KeyEvent.VK_LEFT:bl = false;break;
        }
        calcDirection();
    }

    public Missile fire(){
        int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        Missile missile = new Missile(x,y,ptDir);
        tankClient.missiles.add(missile);
        return missile;
    }

    public void calcDirection(){
        if (bu && !br && !bd && !bl) dir = Direction.U;
        else if (bu && br && !bd && !bl) dir = Direction.RU;
        else if (!bu && br && !bd &&  !bl) dir = Direction.R;
        else if (!bu && br && bd && !bl) dir = Direction.RD;
        else if (!bu && !br && bd && !bl) dir = Direction.D;
        else if (!bu && !br && bd && bl) dir = Direction.LD;
        else if (!bu && !br && !bd && bl) dir = Direction.L;
        else if (bu && !br && !bd && bl) dir = Direction.LU;
        else if (!bu && !br && !bd && !bl) dir = Direction.STOP;
    }
}
