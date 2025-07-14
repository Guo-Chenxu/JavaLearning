package com.qq.qqserver.service;

import com.qq.qqcommon.Message;
import com.qq.qqcommon.MessageType;
import com.qq.qqcommon.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端转发消息服务
 */
public class MessageService {
    /**
     * 群发消息
     *
     * @param message 消息
     */
    public static void sendMessageToAll(Message message) throws IOException {
        ConcurrentHashMap<String, User> validUsers = QQServer.getValidUsers();
        for (String user : validUsers.keySet()) {
            if (!user.equals(message.getSender())) {
                // 当前用户不是发送者, 则转发消息
                if (ManageServerConnectClientThread.userIsOnline(user)) {
                    ServerConnectClientThread serverConnectClientThread =
                            ManageServerConnectClientThread.getThread(user);
                    ObjectOutputStream oos7 = new ObjectOutputStream
                            (serverConnectClientThread.getSocket().getOutputStream());
                    oos7.writeObject(message);
                } else {
                    // 当前用户不在线，添加到离线留言
                    QQServer.addOffLineMessage(message, user);
                }
            }
        }
    }

    /**
     * 单发消息
     *
     * @param message 消息
     */
    public static void sendMessageToOne(Message message) throws IOException {
        if (ManageServerConnectClientThread.userIsOnline(message.getReceiver())) {
            // 接收者在线
            System.out.println("发送成功");
            ServerConnectClientThread serverConnectClientThread =
                    ManageServerConnectClientThread.getThread(message.getReceiver());

            // 转发message
            ObjectOutputStream oos = new ObjectOutputStream
                    (serverConnectClientThread.getSocket().getOutputStream());
            oos.writeObject(message);
        } else if (!QQServer.userExists(message.getReceiver())) {
            // 接收者不存在
            System.out.println("对方不存在, 发送失败");
            ServerConnectClientThread serverConnectClientThread =
                    ManageServerConnectClientThread.getThread(message.getSender());

            // 转发message
            ObjectOutputStream oos = new ObjectOutputStream
                    (serverConnectClientThread.getSocket().getOutputStream());
            Message message3 = new Message();
            message3.setMesType(MessageType.MESSAGE_USER_NOT_EXISTS);
            oos.writeObject(message3);
        } else {
            QQServer.addOffLineMessage(message, message.getReceiver());
        }
    }
}
