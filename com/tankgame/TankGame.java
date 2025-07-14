package com.tankgame;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Scanner;

/**
 * 坦克大战主方法
 */
public class TankGame extends JFrame {
    /** 定义mypanel*/
    MyPanel mp = null;

    public static void main(String[] args) throws IOException {
        TankGame tankgame = new TankGame();
    }

    public TankGame() throws IOException {
        // 游戏状态, 0是开始新游戏, , 1是继续游戏
        int state;
        System.out.println("请输入选择\n 0: 开始新游戏 \n 1: 继续上局游戏");
        Scanner sc = new Scanner(System.in);
        state = Integer.parseInt(sc.next());
        mp = new MyPanel(state);
        new Thread(mp).start();
        // 坦克大战绘图区域
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(1300, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        // 监听关闭窗口事件
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("游戏正在关闭");
                try {
                    Recorder.keepRecord();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
