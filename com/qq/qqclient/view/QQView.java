package com.qq.qqclient.view;

import com.qq.qqclient.service.FileClientService;
import com.qq.qqclient.service.ManageClientConnectServerThread;
import com.qq.qqclient.service.MessageClientService;
import com.qq.qqclient.service.UserClientService;
import utils.*;

import java.io.IOException;

/**
 * 客户端菜单界面
 */
public class QQView {
    /**
     * 控制是否显示菜单
     */
    private boolean loop = true;
    /**
     * 接受键盘输入
     */
    private String key = "";

    /**
     * 用户相关服务
     */
    private UserClientService userClientService = new UserClientService();
    /**
     * 消息服务
     */
    private MessageClientService messageClientService = new MessageClientService();
    /**
     * 文件服务
     */
    private FileClientService fileClientService = new FileClientService();
    /**
     * 是否显示二级菜单
     */
    public static boolean flag = true;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new QQView().mainMenu();
    }

    /**
     * 显示主菜单
     */
    private void mainMenu() throws IOException, ClassNotFoundException {
        while (loop) {

            System.out.println("=========欢迎登录我的盗版QQ=========");
            System.out.println("\t\t 1 登陆系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择: ");
            key = Utility.readString(1);

            // 根据用户输入来选择不同的处理逻辑
            switch (key) {
                case "1":
                    login();
                    break;
                case "9":
                    loop = false;
                    System.out.println("退出成功");
                    break;
                default:
                    break;
            }
        }
    }

    protected void login() throws IOException, ClassNotFoundException {
        System.out.print("请输入用户名: ");
        String userId = Utility.readString(10);
        System.out.print("请输入密 码: ");
        String pwd = Utility.readString(50);
        // 验证 用户名 和 密码 是否正确
        if (userClientService.checkUser(userId, pwd)) {
            System.out.println("欢迎 " + userId + " 登录");
            while (loop) {
                //! 这句话不能删, 要不然就只能处理一次, 神奇bug
                System.out.print("");
                if (QQView.flag) {
                    System.out.println("=========盗版QQ二级菜单 ( 用户 " + userId + " ) =========");
                    System.out.println("\t\t 1 显示在线用户列表");
                    System.out.println("\t\t 2 群发消息");
                    System.out.println("\t\t 3 私聊消息");
                    System.out.println("\t\t 4 发送文件");
                    System.out.println("\t\t 9 退出系统");
                    System.out.print("请输入你的选择: ");
                    key = Utility.readString(1);

                    // 正在处理逻辑, 暂时不显示二级页面
                    QQView.flag = false;

                    switch (key) {
                        case "1":
                            // 获取当前在线用户列表
                            userClientService.onlineUsersList();
                            break;
                        case "2":
                            // 群发消息
                            System.out.print("请输入群发内容: \n\t");
                            String content2 = Utility.readString(100);
                            messageClientService.sendMessageToAll(content2, userId);
                            break;
                        case "3":
                            // 和在线用户私聊
                            System.out.print("请输入私聊对象的用户ID（在线）: \n\t");
                            String receiver3 = Utility.readString(10);
                            System.out.print("请输入发送内容: \n\t");
                            String content3 = Utility.readString(100);
                            messageClientService.sendMessageToOne(content3, userId, receiver3);
                            break;
                        case "4":
                            // 发送文件
                            System.out.print("请输入发送文件对象的用户ID（在线）: \n\t");
                            String receiver4 = Utility.readString(10);
                            System.out.print("请输入所发送文件的路径: \n\t");
                            String src = Utility.readString(200);
                            fileClientService.sendFileToOne(src, userId, receiver4);
                            break;
                        case "9":
                            // 安全退出系统
                            userClientService.logout();
                            loop = false;
                            break;
                        default:
                            QQView.flag = true;
                            break;
                    }
                }
            }
        } else {
            System.out.println("登录失败");
        }
    }
}
