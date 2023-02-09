package com.tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import static java.lang.System.exit;

/**
 * 绘图区域
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    /**
     * 我方坦克
     */
    MyTank mt;
    /**
     * 敌方坦克
     */
    Vector<EnemyTank> ets = new Vector<>();
    int enemyTankCnts = 3;
    AePlayWave aePlayWave = new AePlayWave("D:\\JavaWork\\bili_projects\\src\\com\\tankgame\\tankMusic.wav");

    public MyPanel(int state) throws IOException {
        if (state == 1) {
            File file = new File(Recorder.getRecordFile());
            if (!file.exists()) {
                System.out.println("没有存档, 只能开启新游戏");
                exit(0);
            }
            mt = Recorder.getMtRecord();
            ets = Recorder.getEtsRecord();
        } else {
            // 初始化己方坦克
            mt = new MyTank(200, 300, 0);
            initEnemyTanks();
        }

        // 设置将Recorder中的坦克
        Recorder.setMt(mt);
        Recorder.setEts(ets);

        // 播放音乐
        aePlayWave.start();
    }

    public void showInfo(Graphics g) {
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("累计击毁坦克数量", 1020, 30);
        g.drawString(Recorder.getKillTank() + "", 1080, 80);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);
        showInfo(g);
        if (mt != null && mt.isLive()) {
            // 画出己方坦克
            drawTank(mt.getX(), mt.getY(), g, mt.getDir(), 0);
            // 己方坦克子弹
            for (Shot shot : mt.shots) {
                if (shot.isLive()) {
                    g.fill3DRect(shot.getX(), shot.getY(), 4, 4, false);
                } else {
                    mt.shots.remove(shot);
                }
            }
        }
        // 画出敌方坦克及子弹
        for (EnemyTank et : ets) {
            if (et.isLive()) {
                drawTank(et.getX(), et.getY(), g, et.getDir(), 1);
                for (Shot shot : et.shots) {
                    if (shot.isLive()) {
                        g.fill3DRect(shot.getX(), shot.getY(), 4, 4, false);
                    } else {
                        et.shots.remove(shot);
                    }
                }
            } else {
                ets.remove(et);
            }
        }
        // 敌方坦克全部击毁则重新初始化敌方坦克
        if (ets.isEmpty()){
            initEnemyTanks();
        }
    }

    /**
     * 初始化敌方坦克
     */
    public void initEnemyTanks(){
        for (int i = 0; i < enemyTankCnts; ++i) {
            EnemyTank et = new EnemyTank(100 * (i + 1), 100, 1);
            new Thread(et).start();
            Shot shot = new Shot(et.getX() + 18, et.getY() + 60, 1);
            et.shots.add(shot);
            new Thread(shot).start();
            ets.add(et);
        }
    }

    /**
     * 画坦克
     *
     * @param x         坦克左上角x坐标
     * @param y         坦克左上角y坐标
     * @param g         画笔
     * @param direction 坦克方向
     * @param type      坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        if (type == 0) {
            // 己方
            g.setColor(Color.cyan);
        } else {
            // 敌方
            g.setColor(Color.yellow);
        }
        switch (direction) {
            //方向向上
            case 0: {
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fill3DRect(x + 18, y, 4, 30, true);
                g.fillOval(x + 10, y + 20, 20, 20);
                break;
            }
            // 方向向下
            case 1: {
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fill3DRect(x + 18, y + 30, 4, 30, true);
                g.fillOval(x + 10, y + 20, 20, 20);
                break;
            }
            // 方向向左
            case 2: {
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fill3DRect(x, y + 18, 30, 4, true);
                g.fillOval(x + 20, y + 10, 20, 20);
                break;
            }
            // 方向向右
            default: {
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fill3DRect(x + 30, y + 18, 30, 4, true);
                g.fillOval(x + 20, y + 10, 20, 20);
            }
        }
    }

    /**
     * 子弹攻击坦克
     *
     * @param shot 子弹
     * @param tank 坦克
     * @return boolean
     */
    public boolean hitTank(Shot shot, Tank tank) {
        if (shot == null || tank == null) {
            return false;
        }
        int sx = shot.getX(), sy = shot.getY();
        int tx = tank.getX(), ty = tank.getY();
        if (tank.getDir() == 0 || tank.getDir() == 1) {
            if ((sx >= tx && sx <= tx + 40) && (sy >= ty && sy <= ty + 60)) {
                shot.setLive(false);
                tank.setLive(false);
                return true;
            }
        } else {
            if ((sx >= tx && sx <= tx + 60) && (sy >= ty && sy <= ty + 40)) {
                shot.setLive(false);
                tank.setLive(false);
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (mt.isLive()) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                mt.moveUp();
                mt.setDir(0);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                mt.moveDown();
                mt.setDir(1);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                mt.moveLeft();
                mt.setDir(2);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                mt.moveRight();
                mt.setDir(3);
            }

            if (e.getKeyCode() == KeyEvent.VK_J) {
                if (mt.shots.size() < 10) {
                    mt.shotEnemy();
                }
            }
        }
        // this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 击中敌方坦克
            for (Shot shot : mt.shots) {
                for (EnemyTank et : ets) {
                    if (hitTank(shot, et)) {
                        System.out.println("成功击杀");
                        Recorder.addKillTank();
                    }
                }
            }
            // 击中我方坦克
            for (int i = 0; i < ets.size() && mt.isLive(); ++i) {
                for (int j = 0; j < ets.get(i).shots.size() && mt.isLive(); ++j) {
                    if (hitTank(ets.get(i).shots.get(j), mt)) {
                        System.out.println("你死了");
                    }
                }
            }
            this.repaint();
        }
    }
}
