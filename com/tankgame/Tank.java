package com.tankgame;

import java.util.Vector;

/**
 * 坦克
 */
public class Tank {
    /**
     * 坦克横坐标
     */
    private int x;
    /**
     * 坦克纵坐标
     */
    private int y;
    /**
     * 坦克方向 0123 上下左右
     */
    private int dir;
    /**
     * 坦克速度
     */
    private int speed = 2;
    /**
     * 坦克是否存活
     */
    private boolean isLive = true;

    /**
     * 坦克子弹
     */
    Vector<Shot> shots = new Vector<>();

    public Tank(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void moveUp() {
        if (y - speed > 0) {
            y -= speed;
        }
    }

    public void moveDown() {
        if (y + 60 + speed < 750) {
            y += speed;
        }
    }

    public void moveLeft() {
        if (x - speed > 0) {
            x -= speed;
        }
    }

    public void moveRight() {
        if (x + 60 + speed < 1000) {
            x += speed;
        }
    }

    /**
     * 添加子弹
     */
    public void addShot(Vector<Shot> shots, Tank tank) {
        Shot shot;
        switch (tank.getDir()) {
            case 0:
                shot = new Shot(tank.getX() + 18, tank.getY(), tank.getDir());
                break;
            case 1:
                shot = new Shot(tank.getX() + 18, tank.getY() + 60, tank.getDir());
                break;
            case 2:
                shot = new Shot(tank.getX(), tank.getY() + 18, tank.getDir());
                break;
            default:
                shot = new Shot(tank.getX() + 60, tank.getY() + 18, tank.getDir());
                break;
        }
        shots.add(shot);
        new Thread(shot).start();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getDir() {
        return dir % 4;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
