package com.qq.qqserver.qqframe;

import com.qq.qqserver.service.QQServer;

import java.io.IOException;

/**
 * 该类创建QQServer, 启动后台服务
 */
public class QQFrame {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new QQServer();
    }
}
