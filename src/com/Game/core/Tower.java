package com.Game.core;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tower {

    DPSChart dpsChart;
    private int id, range, damage, cost;
    private String towerType;
    Point position;
    int maxTargets;
    ArrayList<Vectoid> targets;
    int currentTargets;
    String description;
    private BufferedImage sprite;
    int delay, maxDelay;
    CSVWriterCustom writer;

    public Tower(int id, int range, int damage, int cost, String towerType, int maxTargets, BufferedImage sprite, int delay){
        this.id = id;
        this.range = range;
        this.damage = damage;
        this.cost = cost;
        this.towerType = towerType;
        this.maxTargets = maxTargets;
        this.targets = new ArrayList<Vectoid>(maxTargets);
        this.currentTargets = 0;
        this.sprite = sprite;
        this.delay = delay;
        this.maxDelay = delay;
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

    public void setDpsChart(DPSChart dpsChart) {
        this.dpsChart = dpsChart;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public void setWriter(CSVWriterCustom writer) {
        this.writer = writer;
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

    public CSVWriterCustom getWriter() {
        return writer;
    }

    public BufferedImage getSprite() {
        return sprite;
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

    public DPSChart getDpsChart() {
        return dpsChart;
    }

    public boolean IsTargetFull(){
        return maxTargets <= currentTargets;
    }

    public void removeTarget(Vectoid v){
        targets.remove(v);
    }


}
