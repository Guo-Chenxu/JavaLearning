package com.tankgame;

import java.util.Vector;

/**
 * 己方坦克
 */
public class MyTank extends Tank {

    public MyTank(int x, int y, int dir) {
        super(x, y, dir);
    }

    public void shotEnemy() {
        addShot(shots, this);
    }
}
