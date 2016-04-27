package com.matthewyao;

import java.awt.*;
import java.util.List;

/**
 * Created by matthewyao on 2016/4/12.
 */
public class Missile {
    private int x,y;
    public static final int WIDTH = 10,HEIGHT = 10;
    private int X_SPEED = 10,Y_SPEED = 10;
    private Tank.Direction dir;
    private boolean live = true;
    private TankClient tankClient;
    private boolean good;

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Missile(int x, int y,boolean good, Tank.Direction dir) {
        this.x = x;
        this.y = y;
        this.good = good;
        this.dir = dir;
    }

    public Missile(int x, int y,boolean good, Tank.Direction dir,TankClient tankClient) {
        this(x,y,good,dir);
        this.tankClient = tankClient;
    }

    public void draw(Graphics g){
        if (!live) {
            tankClient.missiles.remove(this);
            return;
        }
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.fillOval(x, y, WIDTH, HEIGHT);
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
        if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT){
            live = false;
            tankClient.missiles.remove(this);
        }
    }

    private Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }

    public void hitTank(Tank tank){
        if(live && this.getRect().intersects(tank.getRect()) && tank.isLive() && this.good != tank.isGood()){
            if (tank.isGood()){
                tank.setLife(tank.getLife() - 20);
                if (tank.getLife() <= 0) tank.setLive(false);
            }
            else {
                tank.setLive(false);//坦克消失
            }
            setLive(false);//子弹消失
            tankClient.explodes.add(new Explode(tank.getX(),tank.getY(),tankClient));//爆炸
        }
    }

    public void hitWall(Wall w){
        if (this.live && this.getRect().intersects(w.getRect())){
            this.live = false;
        }
    }

    public void hitTanks(List<Tank> tanks){
        for (Tank tank : tanks){
            hitTank(tank);
        }
    }
}
