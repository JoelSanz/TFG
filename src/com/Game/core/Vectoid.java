package com.Game.core;
import java.awt.*;

public class Vectoid {
    private int id, damage, hp, ms;
    String vectoidType;
    Point currentPosition;

    public Vectoid(int id, int damage, int hp, int ms, String vectoidType){
        this.id = id;
        this.damage = damage;
        this.hp = hp;
        this.ms = ms;
        this.vectoidType = vectoidType;
        
    }

    //Setters & Getters
    public int getId() {
        return id;
    }

    public int getDamage() {
        return damage;
    }

    public int getHp() {
        return hp;
    }

    public int getMs() {
        return ms;
    }

    public String getVectoidType() {
        return vectoidType;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setMs(int ms) {
        this.ms = ms;
    }

    public void setVectoidType(String vectoidType) {
        this.vectoidType = vectoidType;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }
}
