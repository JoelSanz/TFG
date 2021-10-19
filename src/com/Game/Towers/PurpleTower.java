package com.Game.Towers;

import com.Game.core.Tower;

public class PurpleTower extends Tower {

    Tower tower = new Tower();
    public PurpleTower(int id, int range, int damage, int cost){
        tower.setId(id);
        tower.setCost(cost);
        tower.setDamage(damage);
        tower.setRange(range);

    }

    public Tower getTower() {
        return tower;
    }
}
