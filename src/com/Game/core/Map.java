package com.Game.core;

public class Map {

    private int[][] map;

    public Map(String filename){

        ReadMap reader = new ReadMap(filename, this);
    }

    public void setMap(int[][] map) {

        this.map = map;
    }
    public int getPosition(int x, int y){

        return map[x][y];
    }
}
