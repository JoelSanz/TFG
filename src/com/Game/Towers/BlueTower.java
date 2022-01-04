package com.Game.Towers;

import com.Game.core.Tower;


public class BlueTower extends Tower {
    public  BlueTower(){
        super(0, 9*53, 10, 50, "blue", 5);
        setDescription("Fires a medium power laser to 3 targets, this damage is constant.");
    }


}
