package com.tankgame;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

/**
 * 记录相关信息, 和文件交互
 */
public class Recorder {
    /**
     * 击毁敌方坦克数量
     */
    private static int killTank = 0;
    /**
     * 我方坦克
     */
    private static MyTank mt;
    /**
     * 敌方坦克
     */
    private static Vector<EnemyTank> ets = new Vector<>();
    /**
     * 文件路径
     */
    private static final String RECORD_FILE = "D:\\JavaWork\\bili_projects\\src\\com\\tankgame\\myRecord.properties";
    /**
     * 定义IO对象
     */
    private static FileReader fr;
    private static FileWriter fw;
    private static Properties properties = new Properties();

    public static void setMt(MyTank mt) {
        Recorder.mt = mt;
    }

    public static void setEts(Vector<EnemyTank> ets) {
        Recorder.ets = ets;
    }

    public static int getKillTank() {
        return killTank;
    }

    public static String getRecordFile() {
        return RECORD_FILE;
    }

    /**
     * 毁坦克数量增加
     */
    public static void addKillTank() {
        ++killTank;
    }

    /**
     * 当继续游戏时, 恢复我方坦克游戏信息
     */
    public static MyTank getMtRecord() throws IOException {
        fr = new FileReader(RECORD_FILE);
        properties.load(fr);
        int x = Integer.parseInt(properties.getProperty("mtx") + "");
        int y = Integer.parseInt(properties.getProperty("mty") + "");
        int dir = Integer.parseInt(properties.getProperty("mtDir") + "");
        return new MyTank(x, y, dir);
    }

    /**
     * 当继续游戏时, 恢复敌方坦克游戏信息
     */
    public static Vector<EnemyTank> getEtsRecord() throws IOException {
        fr = new FileReader(RECORD_FILE);
        properties.load(fr);
        killTank = Integer.parseInt(properties.getProperty("killTank") + "");
        Vector<EnemyTank> ets = new Vector<>();
        int size = Integer.parseInt(properties.getProperty("etNum") + "");
        for (int i = 0; i < size; ++i) {
            int x = Integer.parseInt(properties.getProperty("etx" + i) + "");
            int y = Integer.parseInt(properties.getProperty("ety" + i) + "");
            int dir = Integer.parseInt(properties.getProperty("etDir" + i) + "");
            EnemyTank et = new EnemyTank(x, y, dir);
            new Thread(et).start();
            ets.add(et);
        }
        return ets;
    }

    /**
     * 当游戏退出时, 保存游戏信息
     */
    public static void keepRecord() throws IOException {
        fw = new FileWriter(RECORD_FILE);
        // 写入消灭坦克数量
        properties.setProperty("killTank", killTank + "");
        // 己方坦克信息
        properties.setProperty("mtx", mt.getX() + "");
        properties.setProperty("mty", mt.getY() + "");
        properties.setProperty("mtDir", mt.getDir() + "");
        // 敌方坦克总数量以及信息
        properties.setProperty("etNum", ets.size() + "");
        for (int i = 0; i < ets.size(); ++i) {
            if (ets.get(i).isLive()) {
                properties.setProperty("etx" + i, ets.get(i).getX() + "");
                properties.setProperty("ety" + i, ets.get(i).getY() + "");
                properties.setProperty("etDir" + i, ets.get(i).getDir() + "");
            }
        }
        try {
            properties.store(fw, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
