package com.qq.qqcommon;

/**
 * 消息类型
 */
public interface MessageType {
    /**
     * 登录成功
     */
    String MESSAGE_LOGIN_SUCCESS = "1";
    /**
     * 登录失败
     */
    String MESSAGE_LOGIN_FAIL = "2";
    /**
     * 普通信息
     */
    String MESSAGE_COMM_MES = "3";
    /**
     * 获取在线用户列表
     */
    String MESSAGE_GET_ONLINE_USERS = "4";
    /**
     * 返回在线用户列表
     */
    String MESSAGE_RET_ONLINE_USERS = "5";
    /**
     * 客户端请求退出
     */
    String MESSAGE_CLIENT_LOGOUT = "6";
    /**
     * 群发消息
     */
    String MESSAGE_TO_ALL = "7";
    /**
     * 文件消息
     */
    String MESSAGE_FILE_MES = "8";
    /**
     * 当前用户不在线
     */
    String MESSAGE_USER_NOT_EXISTS = "100";
}
