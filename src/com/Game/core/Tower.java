package com.Game.core;

public class Tower {
    private int id, range, damage, cost;

    public Tower(){}

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
}
