package com.qq.qqserver.service;

import java.util.HashMap;

/**
 * 管理服务器连接到客户端的线程
 */
public class ManageServerConnectClientThread {
    /**
     * 在线用户线程集合
     */
    private static HashMap<String, ServerConnectClientThread> hashMap = new HashMap<>();

    /**
     * 将某个线程添加到集合中
     * @param userId 用户名
     * @param serverConnectClientThread 服务器连接到客户端的线程
     */
    public static void addThread(String userId, ServerConnectClientThread serverConnectClientThread){
        hashMap.put(userId, serverConnectClientThread);
    }

    /**
     * 通过用户名获取对应线程
     * @param userId 用户名
     * @return 服务器连接到客服端的线程
     */
    public static ServerConnectClientThread getThread(String userId){
        return hashMap.get(userId);
    }

    /**
     * 返回在线用户列表
     * @return 在线用户列表
     */
    public static String getOnlineUsers(){
        StringBuilder res = new StringBuilder("");
        for (String user : hashMap.keySet()){
            res.append(user).append(" ");
        }
        return res.toString();
    }

    /**
     * 用户退出系统, 删除该用户的线程
     * @param userId 用户Id
     */
    public static void deleteUsers(String userId){
        hashMap.remove(userId);
    }

    /**
     * 获取当前在线用户集合
     * @return 当前在线用户集合
     */
    public static HashMap<String, ServerConnectClientThread> getHashMap() {
        return hashMap;
    }

    /**
     * 判断该用户当前是否在线
     * @param userId 用户ID
     * @return 是否在线
     */
    public static boolean userIsOnline(String userId){
        return hashMap.containsKey(userId);
    }
}
