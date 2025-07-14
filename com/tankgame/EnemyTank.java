package com.tankgame;

import java.util.Vector;

/**
 * 敌方坦克
 */
public class EnemyTank extends Tank implements Runnable {

    public EnemyTank(int x, int y, int dir) {
        super(x, y, dir);
    }

    @Override
    public void run() {
        while (true) {
            // 当子弹数量为0时创建子弹
            if (isLive() && shots.size() < 10 && (int) (Math.random() * 10) == 8) {
                addShot(shots, this);
            }

            // 根据当前方向移动
            switch (getDir()) {
                case 0:
                    moveUp();
                    break;
                case 1:
                    moveDown();
                    break;
                case 2:
                    moveLeft();
                    break;
                default:
                    moveRight();
                    break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 随机改变坦克方向
            if ((int) (Math.random() * 30) == 6) {
                setDir((int) (Math.random() * 4));
            }

            // 坦克死亡, 退出线程
            if (!isLive()) {
                break;
            }
        }
    }
}
