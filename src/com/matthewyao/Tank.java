package com.matthewyao;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by matthewyao on 2016/4/11.
 */
public class Tank {
    private int x,y;
    private int X_SPEED = 5,Y_SPEED = 5;
    private Direction dir;

    private boolean bu = false, br = false, bd = false, bl = false;
    public enum Direction{U,RU,R,RD,D,LD,L,LU,STOP}

    public Tank(int y, int x) {
        this.y = y;
        this.x = x;
        dir = Direction.STOP;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, 30, 30);
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

    public void calcDirection(){
        if (bu && !br && !bd && !bl) dir = Direction.U;
        else if (bu && br && !bd && !bl) dir = Direction.RU;
        else if (!bu && br && !bd &&  !bl) dir = Direction.R;
        else if (!bu && br && bd && !bl) dir = Direction.RD;
        else if (!bu && !br && bd && !bl) dir = Direction.D;
        else if (!bu && !br && bd && bl) dir = Direction.LD;
        else if (!bu && !br && !bd && bl) dir = Direction.L;
        else if (bu && !br && !bd && !bl) dir = Direction.LU;
        else if (!bu && !br && !bd && !bl) dir = Direction.STOP;
    }
}
