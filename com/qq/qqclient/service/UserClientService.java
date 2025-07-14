package com.qq.qqclient.service;

import com.qq.qqcommon.Message;
import com.qq.qqcommon.MessageType;
import com.qq.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 完成用户相关服务
 */
public class UserClientService {
    private User u = new User();
    private Socket socket;

    /**
     * 根据 用户名 和 密码 到服务器确认该用户是否合法
     * @param userId 用户名
     * @param pwd 密码
     * @return 是否合法
     */
    public boolean checkUser(String userId, String pwd) throws IOException, ClassNotFoundException {
        u.setUserId(userId);
        u.setPassword(pwd);
        boolean res = false;

        socket = new Socket(InetAddress.getLocalHost(), 9999);
        // 将用户信息发送到服务端
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(u);
        // 读取从服务器回复的信息
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Message message = (Message) ois.readObject();

        if (message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCESS)) {
            // 创建一个和服务器端保持通讯的线程
            ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
            clientConnectServerThread.start();
            clientConnectServerThread.setPriority(Thread.MAX_PRIORITY);
            ManageClientConnectServerThread.addThread(userId, clientConnectServerThread);
            res = true;
        }else {
            System.out.println("当前用户 密码错误 / 已登录 / 未注册");
            socket.close();
        }
        return res;
    }

    public void onlineUsersList() throws IOException {
        // 发送一个message, 类型为获取在线用户列表
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_USERS);
        message.setSender(u.getUserId());

        // 得到当前对象的线程对应的socket的输出流
        ObjectOutputStream oos = new ObjectOutputStream
                (ManageClientConnectServerThread.getThread(u.getUserId()).getSocket().getOutputStream());
        oos.writeObject(message);
    }

    /**
     * 向服务端发送退出系统的消息
     */
    public void logout() throws IOException {
        // 发送一个message, 类型为请求退出系统
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_LOGOUT);
        message.setSender(u.getUserId());

        // 得到当前对象的线程对应的socket的输出流
        ObjectOutputStream oos = new ObjectOutputStream
                (ManageClientConnectServerThread.getThread(u.getUserId()).getSocket().getOutputStream());
        oos.writeObject(message);

        // 退出登录
        System.out.println("退出成功");
        System.exit(0);
    }
}
