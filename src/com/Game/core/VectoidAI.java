package com.Game.core;

import java.awt.*;
import java.util.Random;

public class VectoidAI {

    public Map map;
    int flip;

    public int cellSize;
    Random rand;
    char lastTrajectory;
    public VectoidAI(Map m, int cs){

        this.map = m;
        flip = generateRandomNumber();
        this.cellSize = cs;
        rand = new Random();
        lastTrajectory = 'r';
    }

    public boolean CalculateMove(Vectoid v){
        Point vectoidPosition = v.getCurrentPosition();
        int nextPosition = map.getPosition(vectoidPosition.x+1, vectoidPosition.y);
        if(vectoidPosition.x != 0) {
            if (map.getPosition(vectoidPosition.x + 1, vectoidPosition.y) == 1 && map.getPosition(vectoidPosition.x - 1, vectoidPosition.y) == 1) {
                if (map.getPosition(vectoidPosition.x, vectoidPosition.y - 1) == 1) {
                    if (v.getPrevPosition().y == vectoidPosition.y - 1) {

                        if (!v.hasFlipped()) {
                            if (lastTrajectory == 'l') {
                                MoveRight(v, vectoidPosition);
                                System.out.println(v + "has gone right");
                                v.setFlip(true);
                                lastTrajectory = 'r';
                                return true;


                            } else {
                                MoveLeft(v, vectoidPosition);
                                v.setFlip(true);
                                System.out.println(v + "has gone right");
                                lastTrajectory = 'l';
                                return true;

                            }

                        } else {
                            if (v.getTrajectory() == 'r') {

                                MoveRight(v, vectoidPosition);
                                return true;

                            } else {

                                MoveLeft(v, vectoidPosition);
                                return true;

                            }

                        }
                    }

                } else if (map.getPosition(vectoidPosition.x, vectoidPosition.y + 1) == 1) {
                    MoveDown(v, vectoidPosition);
                    return true;
                } else {
                    if (v.getTrajectory() == 'r') {

                        MoveRight(v, vectoidPosition);
                        return true;

                    } else {

                        MoveLeft(v, vectoidPosition);
                        return true;

                    }

                }
            }
        }
        if(map.getPosition(vectoidPosition.x, vectoidPosition.y+1) == 1 && (vectoidPosition.y + 1 != v.getPrevPosition().y)){

            MoveDown(v, vectoidPosition);
            return true;

        }else if(map.getPosition(vectoidPosition.x, vectoidPosition.y  - 1) == 1 && (vectoidPosition.y - 1 != v.getPrevPosition().y)){

            MoveUp(v, vectoidPosition);
            return true;

        }else if(map.getPosition(vectoidPosition.x + 1, vectoidPosition.y) == 1 && (vectoidPosition.x + 1 != v.getPrevPosition().x)){

            MoveRight(v, vectoidPosition);
            return true;

        }else if(map.getPosition(vectoidPosition.x - 1, vectoidPosition.y) == 1 && (vectoidPosition.x - 1 != v.getPrevPosition().x)){

            MoveLeft(v, vectoidPosition);
            return true;

        }

        if(map.getPosition(vectoidPosition.x, vectoidPosition.y + 1) == 3 ){
            MoveDown(v, vectoidPosition);
            return true;
        }
        return false;
    }

    /**
     *
     * @param v Vectoid object
     * @param vectoidPosition position of vectoid v
     */
    private void MoveRight(Vectoid v, Point vectoidPosition){
        v.setTrajectory('r');
        int msMod = 0;
        if(v.isSlowed())
            msMod=2;
        if(v.getPositionOffset() < cellSize)
            v.setPositionOffset(v.getPositionOffset() + v.getMs() - msMod);
        else{
            v.setPrevPosition(vectoidPosition);
            v.setCurrentPosition(new Point(vectoidPosition.x + 1, vectoidPosition.y));
            v.setPositionOffset(0);
        }

    }

    /**
     *
     * @param v Vectoid object
     * @param vectoidPosition position of vectoid v
     */
    private void MoveLeft(Vectoid v, Point vectoidPosition){
        v.setTrajectory('l');
        int msMod = 0;
        if(v.isSlowed())
            msMod=2;
        if(v.getPositionOffset() < cellSize)
            v.setPositionOffset(v.getPositionOffset() + v.getMs()-msMod);
        else{
            v.setPrevPosition(vectoidPosition);
            v.setCurrentPosition(new Point(vectoidPosition.x - 1, vectoidPosition.y));
            v.setPositionOffset(0);
        }

    }

    /**
     *
     * @param v Vectoid object
     * @param vectoidPosition position of vectoid v
     */
    private void MoveUp(Vectoid v, Point vectoidPosition){
        v.setTrajectory('u');
        int msMod = 0;
        if(v.isSlowed())
            msMod=2;
        if(v.getPositionOffset() < cellSize)
            v.setPositionOffset(v.getPositionOffset() + v.getMs()-msMod);
        else {
            v.setPrevPosition(vectoidPosition);
            v.setCurrentPosition(new Point(vectoidPosition.x, vectoidPosition.y - 1));
            v.setPositionOffset(0);
        }

    }

    /**
     *
     * @param v Vectoid object
     * @param vectoidPosition position of vectoid v
     */
    private void MoveDown(Vectoid v, Point vectoidPosition){
        v.setTrajectory('d');
        int msMod = 0;
        if(v.isSlowed())
            msMod=2;
        if(v.getPositionOffset() < cellSize)
            v.setPositionOffset(v.getPositionOffset() + v.getMs()-msMod);
        else {
            v.setPrevPosition(vectoidPosition);
            v.setCurrentPosition(new Point(vectoidPosition.x, vectoidPosition.y + 1));
            v.setPositionOffset(v.getPositionOffset()-cellSize);
        }

    }

    public boolean hasArrived(Vectoid v){
        if(map.getPosition(v.getCurrentPosition().x, v.getCurrentPosition().y) == 3)
            return true;
        else
            return false;
    }

    private int generateRandomNumber(){
        int upperbound = 10;
        rand = new Random();


        return rand.nextInt(upperbound);
    }
}
