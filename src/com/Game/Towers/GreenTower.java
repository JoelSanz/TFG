package com.Game.Towers;

import com.Game.core.Tower;

public class GreenTower extends Tower {

    public GreenTower(){
        super(1, 3*53, 5, 20, "green", 2);
        setDescription("Fires a low damaging that also slows enemies");
    }
}
