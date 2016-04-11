package com.matthewyao;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by matthewyao on 2016/4/11.
 */
public class TankClient extends Frame{

    public void lauchFrame(){
        this.setLocation(400,300);
        this.setSize(800, 600);
        this.setTitle("TankWar");
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
    }

    public static void main(String[] args) {
        TankClient client = new TankClient();
        client.lauchFrame();
    }
}
