package com.Game.Towers;

import com.Game.core.Tower;

import java.awt.image.BufferedImage;

public class PurpleTower extends Tower {


    public PurpleTower(int cs, BufferedImage sprite){
        super(2, 8*cs, 2650, 50, "purple", 1, sprite, 20);
        setDescription("Fires a high powered laser, this shot has cooldown");
    }
}
