package com.matthewyao;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created by matthewyao on 2016/4/11.
 */
public class Tank {
    private int x,y;

    private int oldX,oldY;

    private int life = 100;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int X_SPEED = 5,Y_SPEED = 5;
    public static final int WIDTH = 30,HEIGHT = 30;
    private Direction dir,ptDir;
    private boolean good;

    public boolean isGood() {
        return good;
    }

    private boolean live = true;
    TankClient tankClient;
    private static Random random = new Random();
    int step = random.nextInt(10) + 5;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    private boolean bu = false, br = false, bd = false, bl = false;
    public enum Direction{U,RU,R,RD,D,LD,L,LU,STOP}

    public Tank(int x, int y,Direction dir,boolean good) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        this.good = good;
        this.dir = dir;
        ptDir = Direction.U;
    }

    public Tank(int x, int y,boolean good,Direction dir, TankClient tankClient) {
        this(x,y,dir,good);
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
        if (!live) {
            tankClient.tanks.remove(this);
            return;
        }
        Color c = g.getColor();
        if (good) g.setColor(Color.RED);
        else g.setColor(Color.BLUE);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(Color.black);
        drawPt(g);
        g.setColor(c);
        move();
    }

    public void move(){

        oldX = x;
        oldY = y;

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
        if (!good){
            Direction[] dirs = Direction.values();
            if (step == 0){
                step = random.nextInt(10) + 5;
                int rn = random.nextInt(dirs.length);
                dir = dirs[rn];
            }
            step--;
            if (random.nextInt(50) > 48) fire();
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

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_CONTROL:
                fire();break;
            case KeyEvent.VK_UP:bu = false;break;
            case KeyEvent.VK_RIGHT:br = false;break;
            case KeyEvent.VK_DOWN:bd = false;break;
            case KeyEvent.VK_LEFT:bl = false;break;
            case KeyEvent.VK_A:superFire();break;
        }
        calcDirection();
    }

    public Missile fire(){
        if (!live) return null;
        int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        Missile missile = new Missile(x,y,good,ptDir,tankClient);
        tankClient.missiles.add(missile);
        return missile;
    }

    public Missile fire(Direction dir){
        if (!live) return null;
        int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        Missile missile = new Missile(x,y,good,dir,tankClient);
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

    public Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }

    private void stay(){
        this.x = oldX;
        this.y = oldY;
    }

    public void collidesWithWall(Wall w){
        if (this.live && this.getRect().intersects(w.getRect())){
            this.stay();
        }
    }

    public void collidesWithTanks(java.util.List<Tank> tanks){
        for (Tank tank : tanks){
            if (this != tank){
                if (this.live && tank.isLive() && this.getRect().intersects(tank.getRect())){
                    this.stay();
                    tank.stay();
                }
            }
        }
    }

    public void superFire(){
        Direction[] dirs = Direction.values();
        for (Direction dir : dirs) {
            if (dir != Direction.STOP){
                fire(dir);
            }
        }
    }
}
