package com.Game.Towers;

import com.Game.core.Tower;

public class RedTower extends Tower {

    Tower tower = new Tower();
    public RedTower(int id, int range, int damage, int cost){
        tower.setId(id);
        tower.setCost(cost);
        tower.setDamage(damage);
        tower.setRange(range);

    }

    public Tower getTower() {
        return tower;
    }
}
