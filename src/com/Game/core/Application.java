package com.Game.core;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {
    public Application(){
        initUI();
    }
    private void initUI(){
        add(new Board());

        pack();
        setResizable(true);
        //setUndecorated(true);
        setTitle("Test Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    public static void main(String [] args){
        EventQueue.invokeLater(()->{
            Application ex = new Application();
            ex.setVisible(true);
        });
    }
}