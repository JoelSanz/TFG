package com.Game.core;

import javax.swing.*;
import java.awt.*;

public class Frame extends JDialog {

    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int h = device.getDisplayMode().getHeight();
    int w = device.getDisplayMode().getWidth();

    public Frame(){

        initUI();
    }
    private void initUI(){

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setUndecorated(false);
        this.setVisible(true);
        this.setModalityType (ModalityType.APPLICATION_MODAL);
        device.setFullScreenWindow(this);
        Board screen = new Board(this);
        this.add(screen);


    }
    public static void main(String [] args){
        EventQueue.invokeLater(()->{
            Frame ex = new Frame();
            ex.setVisible(true);
        });
    }
}
