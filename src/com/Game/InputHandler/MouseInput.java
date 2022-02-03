package com.Game.InputHandler;

import com.Game.core.Board;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {



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
        if(board.isMainMenu())
            board.checkMainMenuClick(e.getX(), e.getY());
        else if(board.isCreatingLevel())
            board.checkCreatingLevelClick(e.getX(), e.getY());
        else if(board.isSelectingLevel()){
            System.out.println("selecting level click");
            board.checkSelectingLevelClick(e.getX(), e.getY());
        }else
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
