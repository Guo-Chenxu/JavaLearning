package com.qq.qqclient.service;

import com.qq.qqclient.view.QQView;
import com.qq.qqcommon.Message;
import com.qq.qqcommon.MessageType;
import utils.Utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 客户端连接到服务器端线程
 */
public class ClientConnectServerThread extends Thread {
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // 用Thread在后台和服务器通信
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();

                // 判断message并进行相应处理
                switch (message.getMesType()) {
                    case MessageType.MESSAGE_RET_ONLINE_USERS:
                        // 获取在线用户列表
                        String[] onlineUsers = message.getContent().split(" ");
                        System.out.println("==========当前在线用户列表=========");
                        for (String s : onlineUsers) {
                            System.out.println("用户: " + s);
                        }
                        break;
                    case MessageType.MESSAGE_COMM_MES:
                        // 接收到用户聊天消息
                        System.out.println("\n" + message.getSender() + " 对你说: ");
                        System.out.println("\t" + message.getContent());
                        break;
                    case MessageType.MESSAGE_USER_NOT_EXISTS:
                        // 当前用户不在线, 无法收到消息
                        System.out.println("\n发送失败, 对方不存在, 无法收到消息");
                    case MessageType.MESSAGE_TO_ALL:
                        // 接收到群发消息
                        System.out.println("\n" + message.getSender() + " 对大家说: ");
                        System.out.println("\t" + message.getContent());
                        break;
                    case MessageType.MESSAGE_FILE_MES:
                        // 接受到文件消息
                        System.out.print("\n" + message.getSender() + " 向你发送了文件, 请输入保存路径: \n\t");
                        // todo 输入读取问题, 和主线程冲突, 需要输入两遍干掉主线程
                        String dest = Utility.readString(200);
                        System.out.println(dest);
                        FileOutputStream fileOutputStream = new FileOutputStream(dest);
                        fileOutputStream.write(message.getFile());
                        fileOutputStream.close();
                        System.out.println("接收完成");
                        break;
                    default:
                        break;
                }
                // 处理完毕, 显示二级页面
                QQView.flag = true;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
