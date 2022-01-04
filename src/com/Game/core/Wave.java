package com.Game.core;

import com.Game.Vectoids.BlueVectoid;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Wave {
    private int vectoidsThisWave;
    private int spawned;
    private boolean isSpawning;
    Board board;
    Vectoid v;
    ArrayList<Point> spawnPoint;
    ArrayList<Vectoid> vectoidList;

    public Wave(Board b){
        this.board = b;
    }

    public void startWave(int vtw){
        spawned = 0;
        isSpawning = true;
        vectoidsThisWave = vtw;

    }

    public void spawnVectoid(ArrayList<Vectoid> vl, Point sp){
        v = new BlueVectoid();
        v.setCurrentPosition(sp);
        v.setPrevPosition(sp);
        vl.add(v);
        spawned++;

    }

    public void setSpawnPoint(ArrayList<Point> p) {
        spawnPoint = p;

    }

    public boolean doneSpawning(){
        if(spawned >= vectoidsThisWave)
            return true;
        else
            return false;
    }

}
