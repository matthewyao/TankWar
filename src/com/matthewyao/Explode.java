package com.matthewyao;

import java.awt.*;

/**
 * Created by matthewyao on 2016/4/16.
 */
public class Explode {
    private int x,y;
    private boolean live = true;
    private int[] diarameters = {8,17,29,45,23,3};
    int step = 0;

    TankClient tankClient;

    public Explode(int x,int y,TankClient tankClient){
        this.x = x;
        this.y = y;
        this.tankClient = tankClient;
    }

    public void draw(Graphics g){
        if (!live) {
            tankClient.explodes.remove(this);
            return;
        }
        if (step == diarameters.length) {
            step = 0;
            live = false;
        }
        Color c = g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(x,y,diarameters[step],diarameters[step]);
        g.setColor(c);
        step++;
    }
}
