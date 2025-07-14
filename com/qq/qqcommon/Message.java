package com.qq.qqcommon;

import java.io.Serial;
import java.io.Serializable;
import java.time.format.SignStyle;

/**
 * 通讯时的消息对象
 */
public class Message implements Serializable {
    /**
     * 序列化兼容性
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 发送者
     */
    private String sender;
    /**
     * 接收者
     */
    private String receiver;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 文件数组
     */
    private byte[] file;
    /**
     * 发送时间
     */
    private String sendTime;
    /**
     * 消息类型
     */
    private String mesType;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }
}
