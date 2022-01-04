package com.Game.Towers;

import com.Game.core.Tower;

public class PurpleTower extends Tower {


    public PurpleTower(){
        super(2, 250, 5, 75, "purple", 2);
        setDescription("Fires a high powered laser, this shot has cooldown");
    }
}
