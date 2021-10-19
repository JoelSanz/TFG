package com.Game.Towers;

import com.Game.core.Tower;


public class BlueTower extends Tower {

    Tower tower = new Tower();
    public  BlueTower(int id, int range, int damage, int cost){
        tower.setId(id);
        tower.setCost(cost);
        tower.setDamage(damage);
        tower.setRange(range);
    }

    public Tower getTower() {
        return tower;
    }
}
