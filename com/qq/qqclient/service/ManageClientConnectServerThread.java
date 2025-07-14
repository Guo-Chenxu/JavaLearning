package com.qq.qqclient.service;

import java.util.HashMap;

/**
 * 管理客户端连接到服务器的线程
 */
public class ManageClientConnectServerThread {
    private static HashMap<String, ClientConnectServerThread> hashMap = new HashMap<>();

    /**
     * 将某个线程添加到集合中
     * @param userId 用户名
     * @param clientConnectServerThread 客户端连接到服务器的线程
     */
    public static void addThread(String userId, ClientConnectServerThread clientConnectServerThread){
        hashMap.put(userId, clientConnectServerThread);
    }

    /**
     * 通过用户名获取对应线程
     * @param userId 用户名
     * @return 客户端连接到服务器的线程
     */
    public static ClientConnectServerThread getThread(String userId){
        return hashMap.get(userId);
    }
}
