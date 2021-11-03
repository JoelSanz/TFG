package com.Game.InputHandler;

import com.Game.core.Board;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {


    int CELL_SIZE = 53;
    private Board board;
    public MouseInput(Board b) {
        this.board = b;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //checkLocationClicked(e.getX(), e.getY());

    }

    @Override
    public void mousePressed(MouseEvent e) {
        board.checkLocationClicked(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
