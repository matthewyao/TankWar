package com.matthewyao;

import java.awt.*;

/**
 * Created by matthewyao on 2016/4/11.
 */
public class TankClient extends Frame{

    public void lauchFrame(){
        this.setLocation(400,300);
        this.setSize(800,600);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        TankClient client = new TankClient();
        client.lauchFrame();
    }
}
