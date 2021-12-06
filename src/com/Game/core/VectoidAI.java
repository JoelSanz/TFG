package com.Game.core;

import java.awt.*;

public class VectoidAI {

    public Map map;
    boolean flip;
    public VectoidAI(Map m){

        this.map = m;
        flip = false;
    }

    public void CalculateMove(Vectoid v){
        Point vectoidPosition = v.getCurrentPosition();
        if(map.getPosition(vectoidPosition.x + 1, vectoidPosition.y) == 1 && map.getPosition(vectoidPosition.x - 1, vectoidPosition.y) == 1){
            if(map.getPosition(vectoidPosition.x, vectoidPosition.y  - 1) == 1 ){
                if(v.getPrevPosition().y == vectoidPosition.y  - 1){
                    //System.out.println("should not go up");
                    if(flip){
                        MoveRight(v, vectoidPosition);
                        flip = !flip;
                    }else{
                        MoveLeft(v, vectoidPosition);
                        flip = !flip;
                    }

                }else{

                    MoveUp(v, vectoidPosition);
                }



            }else if(map.getPosition(vectoidPosition.x, vectoidPosition.y+1) == 1 ){
                MoveDown(v, vectoidPosition);
            }else{
                if(v.getTrajectory() == 'r'){

                    MoveRight(v, vectoidPosition);

                }else{

                    MoveLeft(v, vectoidPosition);

                }

            }
        }else if(map.getPosition(vectoidPosition.x, vectoidPosition.y+1) == 1 && (vectoidPosition.y + 1 != v.getPrevPosition().y)){

            MoveDown(v, vectoidPosition);

        }else if(map.getPosition(vectoidPosition.x, vectoidPosition.y  - 1) == 1 && (vectoidPosition.y - 1 != v.getPrevPosition().y)){

            MoveUp(v, vectoidPosition);

        }else if(map.getPosition(vectoidPosition.x + 1, vectoidPosition.y) == 1 && (vectoidPosition.x + 1 != v.getPrevPosition().x)){

            MoveRight(v, vectoidPosition);

        }else if(map.getPosition(vectoidPosition.x - 1, vectoidPosition.y) == 1 && (vectoidPosition.x - 1 != v.getPrevPosition().x)){

            MoveLeft(v, vectoidPosition);

        }
    }

    /**
     *
     * @param v Vectoid object
     * @param vectoidPosition position of vectoid v
     */
    private void MoveRight(Vectoid v, Point vectoidPosition){
        v.setTrajectory('r');
        if(v.getPositionOffset() <= 50)
            v.setPositionOffset(v.getPositionOffset() + 1);
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
        if(v.getPositionOffset() <= 50)
            v.setPositionOffset(v.getPositionOffset() + 1);
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
        if(v.getPositionOffset() <= 50)
            v.setPositionOffset(v.getPositionOffset() + 1);
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
        if(v.getPositionOffset() <= 50)
            v.setPositionOffset(v.getPositionOffset() + 1);
        else {
            v.setPrevPosition(vectoidPosition);
            v.setCurrentPosition(new Point(vectoidPosition.x, vectoidPosition.y + 1));
            v.setPositionOffset(0);
        }

    }
}

/*
            if(v.getPositionOffset() <= 50){
                if(flip){
                    flip = !flip;
                    v.setTrajectory('r');
                }else{
                    flip = !flip;
                    v.setTrajectory('l');
                }
            }else{
                v.setPositionOffset(v.getPositionOffset() + 1);
            }
 */