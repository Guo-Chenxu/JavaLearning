package com.houserent;

import java.util.Scanner;

public class exit {
    // 退出系统
    public static boolean exit(){
        System.out.println("----------------------------------exit-----------------------------------");
        while(true){
            System.out.print("Are you sure?[y/n]  ");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.next();
            if (str.equals("y")) {
                System.out.println("------------------------------exit success-------------------------------");
                return false;
            } else if (str.equals("n")) {
                return true;
            } else {
                System.out.println("Input wrong, try again.");
            }
        }
    }
}

