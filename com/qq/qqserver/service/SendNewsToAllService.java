package com.qq.qqserver.service;

import com.qq.qqcommon.Message;
import com.qq.qqcommon.MessageType;
import utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;

/**
 * 服务器给所有人推送新闻
 */
public class SendNewsToAllService implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.println("请输入要推送的消息 ( 输入 exit 退出推送服务) : ");
            String news = Utility.readString(100);

            if ("exit".equals(news)) {
                System.out.println("退出推送服务");
                break;
            }

            // 构建群发消息
            Message message = new Message();
            message.setMesType(MessageType.MESSAGE_COMM_MES);
            message.setContent(news);
            message.setSender("服务器");
            message.setSendTime(new Date().toString());
            System.out.println("在 " + message.getSendTime() + " 服务器给所人说: " + message.getContent());

            // 推送消息
            HashMap<String, ServerConnectClientThread> hashMap =
                    ManageServerConnectClientThread.getHashMap();
            for (String user : hashMap.keySet()) {
                // 当前用户不是发送者, 则转发消息
                ServerConnectClientThread serverConnectClientThread =
                        ManageServerConnectClientThread.getThread(user);
                try {
                    ObjectOutputStream oos = new ObjectOutputStream
                            (serverConnectClientThread.getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
