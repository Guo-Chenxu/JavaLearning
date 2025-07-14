package com.qq.qqclient.service;

import com.qq.qqclient.view.QQView;
import com.qq.qqcommon.Message;
import com.qq.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 完成用户之间的消息相关服务
 */
public class MessageClientService {

    /**
     * 和在线用户私聊消息
     *
     * @param content  消息内容
     * @param sender   发送者
     * @param receiver 接收者
     */
    public void sendMessageToOne(String content, String sender, String receiver) throws IOException {
        // 新建消息对象
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println("你对 " + receiver + " 说: \n\t" + content);

        // 向服务端发送消息
        ObjectOutputStream oos = new ObjectOutputStream
                (ManageClientConnectServerThread.getThread(sender).getSocket().getOutputStream());
        oos.writeObject(message);

        // 显示二级页面
        QQView.flag = true;
    }

    /**
     * 给在线用户群发消息
     *
     * @param content 消息内容
     * @param sender  发送者
     */
    public void sendMessageToAll(String content, String sender) throws IOException {
        // 新建消息对象
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver("");
        message.setMesType(MessageType.MESSAGE_TO_ALL);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println("你对大家说: \n\t" + content);

        // 向服务端发送消息
        ObjectOutputStream oos = new ObjectOutputStream
                (ManageClientConnectServerThread.getThread(sender).getSocket().getOutputStream());
        oos.writeObject(message);

        // 显示二级页面
        QQView.flag = true;
    }
}
