package com.Game.Towers;

import com.Game.core.Tower;

import java.awt.image.BufferedImage;

public class RedTower extends Tower {

    public RedTower(int cs, BufferedImage sprite){
        super(3, 7*cs, 110, 200, "red", 7, sprite, 5);
        setDescription("Burns enemies for a great amount of damage");
    }
}
