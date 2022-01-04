package com.Game.core;

import java.awt.*;
import java.util.ArrayList;

public class Tower {
    private int id, range, damage, cost;
    private String towerType;
    Point position;
    int maxTargets;
    ArrayList<Vectoid> targets;
    int currentTargets;
    private final int CELL_SIZE=53;
    String description;

    public Tower(int id, int range, int damage, int cost, String towerType, int maxTargets){
        this.id = id;
        this.range = range;
        this.damage = damage;
        this.cost = cost;
        this.towerType = towerType;
        this.maxTargets = maxTargets;
        this.targets = new ArrayList<Vectoid>(maxTargets);
        this.currentTargets = 0;
    }

    //Getters & Setters
    public void setId(int id){
        this.id = id;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setDescription(String d){
        this.description = d;
    }

    public int getCost() {
        return cost;
    }

    public int getDamage() {
        return damage;
    }

    public int getId() {
        return id;
    }

    public int getRange() {

        return range;
    }

    public String getTowerType() {
        return towerType;
    }

    public Point getPosition(){
        return position;
    }

    public ArrayList<Vectoid> getTargets(){
        return targets;
    }

    public String getDescription(){
        return description;
    }

    public boolean IsTargetFull(){
        return maxTargets <= currentTargets;
    }

    public void removeTarget(Vectoid v){
        targets.remove(v);
    }


}
