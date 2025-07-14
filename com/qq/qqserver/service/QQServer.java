package com.qq.qqserver.service;

import com.qq.qqcommon.Message;
import com.qq.qqcommon.MessageType;
import com.qq.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器, 监听9999端口
 */
public class QQServer {
    private ServerSocket serverSocket;
    /**
     * 创建一个集合, 存放所有合法用户
     * todo 数据库储存
     */
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, User> getValidUsers() {
        return validUsers;
    }

    /**
     * 存放离线留言
     * todo 数据库存储
     */
    private static ConcurrentHashMap<String, ArrayList<Message>> offLineMessage = new ConcurrentHashMap<>();

    static {
        validUsers.put("100", new User("100", "100"));
        validUsers.put("200", new User("200", "200"));
        validUsers.put("300", new User("300", "300"));
    }

    /**
     * 验证用户是否合法
     *
     * @param userId 用户Id
     * @param pwd    用户密码
     * @return 是否合法
     */
    public static boolean checkUser(String userId, String pwd) {
        if (!validUsers.containsKey(userId)) {
            System.out.println("用户 " + userId + " 未注册");
            return false;
        } else if (ManageServerConnectClientThread.getHashMap().containsKey(userId)) {
            System.out.println("用户 " + userId + " 已登录");
            return false;
        } else if (!validUsers.get(userId).getPassword().equals(pwd)) {
            System.out.println("用户 " + userId + " 密码错误");
            return false;
        }
        return true;
    }

    /**
     * 验证用户是否存在
     *
     * @param userId 用户ID
     */
    public static boolean userExists(String userId) {
        return validUsers.containsKey(userId);
    }

    public QQServer() {
        System.out.println("服务器已启动");
        try {
            this.serverSocket = new ServerSocket(9999);
            new Thread(new SendNewsToAllService()).start();

            // 建立连接后一直监听
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                User user = (User) ois.readObject();
                Message message = new Message();

                if (checkUser(user.getUserId(), user.getPassword())) {
                    System.out.println("用户 " + user.getUserId() + " 登录成功");
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCESS);
                    oos.writeObject(message);
                    // 创建一个线程保持通信
                    ServerConnectClientThread serverConnectClientThread =
                            new ServerConnectClientThread(socket, user.getUserId());
                    serverConnectClientThread.start();
                    // 将该线程放到一个集合里
                    ManageServerConnectClientThread.addThread(user.getUserId(), serverConnectClientThread);
                    // 发送离线留言
                    sendOffLineMessage(user.getUserId());
                } else {
                    System.out.println("用户 " + user.getUserId() + " 登录失败");
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    socket.close();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert serverSocket != null;
                serverSocket.close();
                System.out.println("服务器已关闭");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 添加离线留言
     *
     * @param message 信息
     */
    public static void addOffLineMessage(Message message, String receiver) {
        ArrayList<Message> messages = offLineMessage.getOrDefault(receiver, new ArrayList<Message>());
        messages.add(message);
        offLineMessage.put(receiver, messages);
    }

    /**
     * 发送离线留言
     *
     * @param receiver 接收者
     */
    public void sendOffLineMessage(String receiver) throws IOException {
        if (offLineMessage.containsKey(receiver)) {
            ArrayList<Message> messages = offLineMessage.get(receiver);
            for (Message message : messages) {
                if ("".equals(message.getReceiver())) {
                    MessageService.sendMessageToAll(message);
                } else {
                    MessageService.sendMessageToOne(message);
                }
            }
            offLineMessage.remove(receiver);
        }
    }
}
