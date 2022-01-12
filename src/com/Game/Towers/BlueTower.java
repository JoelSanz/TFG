package com.Game.Towers;

import com.Game.core.Tower;


public class BlueTower extends Tower {
    public  BlueTower(){
        super(0, 9*53, 5, 50, "blue", 1);
        setDescription("Fires a medium power laser to 3 targets, this damage is constant.");
    }


}
