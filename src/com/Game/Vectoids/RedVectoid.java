package com.Game.Vectoids;

import com.Game.core.Vectoid;

import java.awt.image.BufferedImage;

public class
RedVectoid extends Vectoid {
    public RedVectoid(int hp,  BufferedImage sprite) {
        super(1, 1, hp, 4, "red", 0, sprite);
    }

}
