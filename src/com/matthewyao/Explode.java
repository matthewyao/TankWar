package com.matthewyao;

import java.awt.*;

/**
 * Created by matthewyao on 2016/4/16.
 */
public class Explode {
    private int x,y;
    private boolean live = true;
    private int[] diarameters = {2,5,10,17,29,45,66,40,13,3};
    int step = 0;

    TankClient tankClient;

    public Explode(int x,int y,TankClient tankClient){
        this.x = x;
        this.y = y;
        this.tankClient = tankClient;
    }

    public void draw(Graphics g){
        if (step == diarameters.length) {
            step = 0;
        }
        Color c = g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(x,y,diarameters[step],diarameters[step]);
        g.setColor(c);
        step++;
    }
}
