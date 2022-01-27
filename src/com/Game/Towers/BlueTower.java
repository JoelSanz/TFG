package com.Game.Towers;

import com.Game.core.Tower;

import java.awt.image.BufferedImage;


public class BlueTower extends Tower {
    public  BlueTower(int cs, BufferedImage sprite){
        super(0, 6*cs, 500, 50, "blue", 4, sprite, 5);
        setDescription("Fires freezing laser that slows Vectoids");
    }


}
