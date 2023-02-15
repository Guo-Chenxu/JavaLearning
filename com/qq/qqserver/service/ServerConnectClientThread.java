package com.qq.qqserver.service;

import com.qq.qqclient.service.ManageClientConnectServerThread;
import com.qq.qqcommon.Message;
import com.qq.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * 服务器端保持和客户端的通信
 */
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {
        boolean loop = true;

        while (loop) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();

                // 判断message类型, 进行处理
                switch (message.getMesType()) {
                    case MessageType.MESSAGE_GET_ONLINE_USERS:
                        // 获取在线用户列表
                        System.out.println(message.getSender() + " 正在拉取在线用户列表");
                        String users = ManageServerConnectClientThread.getOnlineUsers();

                        // 设置message
                        Message message1 = new Message();
                        message1.setMesType(MessageType.MESSAGE_RET_ONLINE_USERS);
                        message1.setContent(users);
                        message1.setReceiver(message.getSender());

                        // 返回message
                        ObjectOutputStream oos1 = new ObjectOutputStream(socket.getOutputStream());
                        oos1.writeObject(message1);
                        break;
                    case MessageType.MESSAGE_COMM_MES:
                        // 私聊发消息
                        System.out.println(message.getSender() + " 在 " + message.getSendTime()
                                + " 向 " + message.getReceiver() + " 发消息");
                        MessageService.sendMessageToOne(message);

                        break;
                    case MessageType.MESSAGE_TO_ALL:
                        // 群发消息
                        System.out.println(message.getSender() + " 在 " + message.getSendTime() + " 群发消息");
                        MessageService.sendMessageToAll(message);

                        break;
                    case MessageType.MESSAGE_FILE_MES:
                        // 发送文件
                        System.out.println(message.getSender() + " 在 " + message.getSendTime()
                                + " 向 " + message.getReceiver() + " 发送了文件");
                        MessageService.sendMessageToOne(message);

                        break;
                    case MessageType.MESSAGE_CLIENT_LOGOUT:
                        // 从集合中删除该用户的线程
                        ManageServerConnectClientThread.deleteUsers(message.getSender());
                        System.out.println("用户 " + message.getSender() + " 退出登录");
                        // 关闭连接
                        socket.close();

                        // 结束该线程
                        loop = false;
                        break;
                    default:
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public Socket getSocket() {
        return socket;
    }
}
