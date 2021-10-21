package com.Game.InputHandler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    MouseEvent event;
    public void MouseInput(){

    }

    public MouseEvent getEvent() {
        return event;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        event = e;
        System.out.println("click registrado");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        event = e;
        System.out.println("press registrado");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        event = e;
        System.out.println("release registrado");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        event = e;
        System.out.println("has entrado en zona registrable");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        event = e;
        System.out.println("has salido de zona registrable");
    }
}
