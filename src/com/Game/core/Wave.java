package com.Game.core;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    public void spawnVectoid(ArrayList<Vectoid> vl, Point sp, int wc, BufferedImage sprite, Vectoid vectoidThisWave){
        try {
            v = (Vectoid) vectoidThisWave.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
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
