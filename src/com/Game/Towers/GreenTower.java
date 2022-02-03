package com.Game.Towers;

import com.Game.core.Tower;

import java.awt.image.BufferedImage;

public class GreenTower extends Tower {

    public GreenTower(int cs, BufferedImage sprite){
        super(1, 6*cs, 22, 20, "green", 1, sprite, 0);
        setDescription("Fires laser that constantly damages Vectoids");
    }
}
