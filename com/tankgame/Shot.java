package com.tankgame;

import java.util.Vector;

/**
 * 子弹
 */
public class Shot implements Runnable {
    /**
     * 子弹横坐标
     */
    private int x;
    /**
     * 子弹纵坐标
     */
    private int y;
    /**
     * 子弹方向 0123 上下左右
     */
    private int direct;
    /**
     * 子弹速度
     */
    private int speed = 3;
    /**
     * 子弹是否存活
     */
    private boolean isLive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirect() {
        return direct;
    }

    public boolean isLive() {
        return isLive;
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    @Override
    public void run() {
        while (true) {
            switch (direct) {
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
            // System.out.println("x = " + x + "  y = " + y);
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                this.isLive = false;
                // System.out.println("退出");
                break;
            }
        }
    }
}
