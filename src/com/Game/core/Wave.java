package com.Game.core;

import com.Game.Vectoids.BlueVectoid;
import java.awt.*;

public class Wave {
    private int vectoidsThisWave;
    private int spawned;
    private boolean isSpawning;
    Board board;
    Vectoid v;
    Point[] spawnPoint;

    public Wave(Board b){
        this.board = b;
    }

    public void startWave(int vtw){
        spawned = 0;
        isSpawning = true;
        vectoidsThisWave = vtw;
        spawnVectoid();
    }
    public void spawnVectoid(){
        int i = 0;
        int j = 0;
        while(spawned < vectoidsThisWave){

            v = new BlueVectoid(1, 1, 100, 1, "blue", 0);
            v.setCurrentPosition(spawnPoint[i]);
            v.setPrevPosition(spawnPoint[i]);
            //this could be done with a set function
            board.vectoidList[j] = v;
            spawned++;
            if(spawnPoint[i + 1] != null)
                i++;
            j++;
        }
    }
    public void setSpawnPoint(Point[] p) {
        spawnPoint = p;

    }


}
