package com.qq.qqclient.service;

import com.qq.qqclient.view.QQView;
import com.qq.qqcommon.Message;
import com.qq.qqcommon.MessageType;
import utils.StreamUtils;

import java.io.*;
import java.util.Date;

/**
 * 文件传输服务
 */
public class FileClientService {
    /**
     * 像一个用户传输文件
     *
     * @param src      待发送文件路径
     * @param sender   发送者ID
     * @param receiver 接收者ID
     */
    public void sendFileToOne(String src, String sender, String receiver) throws IOException {
        // 新建文件消息对象
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setSendTime(new Date().toString());

        // 读取文件
        File file = new File(src);
        if (file.exists()){
            FileInputStream fileInputStream = new FileInputStream(src);
            message.setFile(StreamUtils.streamToByteArray(fileInputStream));
            fileInputStream.close();

            // 向服务端发送消息
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConnectServerThread.getThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        }else {
            System.out.println("发送失败, 文件不存在");
        }
        // 显示二级页面
        QQView.flag = true;
    }
}
