package com.Game.core;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Vectoid implements Cloneable{
    private int id, damage, hp, ms, positionOffset, maxHp;
    String vectoidType;
    Point currentPosition, prevPosition;
    char  trajectory;
    boolean flip;
    BufferedImage sprite;
    boolean arrived;
    boolean slowed;

    public Vectoid(int id, int damage, int hp, int ms, String vectoidType, int po, BufferedImage sprite){
        this.flip = false;
        this.id = id;
        this.damage = damage;
        this.hp = hp;
        this.ms = ms;
        this.vectoidType = vectoidType;
        this.positionOffset = po;
        this.maxHp = hp;
        this.sprite = sprite;
        arrived = false;

        
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

    public BufferedImage getSprite() {
        return sprite;
    }

    public boolean isArrived() {
        return arrived;
    }

    public String getVectoidType() {
        return vectoidType;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public Point getPrevPosition() {
        return prevPosition;
    }

    public int getPositionOffset() {
        return positionOffset;
    }

    public char getTrajectory() {
        return trajectory;
    }

    public boolean isSlowed() {
        return slowed;
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

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public void setVectoidType(String vectoidType) {
        this.vectoidType = vectoidType;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public void setSlowed(boolean slowed) {
        this.slowed = slowed;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setPrevPosition(Point prevPosition) {
        this.prevPosition = prevPosition;
    }

    public void setPositionOffset(int positionOffset) {
        this.positionOffset = positionOffset;
    }

    public void setTrajectory(char trajectory) {
        this.trajectory = trajectory;
    }

    public double getHpPercent(){
        return (double) hp/maxHp;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return (Vectoid)super.clone();
    }

    public boolean hasFlipped(){
        return this.flip;
    }
}
