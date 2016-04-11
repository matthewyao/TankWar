package com.matthewyao;

import java.awt.*;

/**
 * Created by matthewyao on 2016/4/12.
 */
public class Missile {
    private int x,y;
    private int X_SPEED = 10,Y_SPEED = 10,RADIO = 10;
    private Tank.Direction dir;

    public Missile(int x, int y, Tank.Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.fillOval(x, y, RADIO, RADIO);
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
        }
    }
}
