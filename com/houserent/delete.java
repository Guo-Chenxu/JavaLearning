package com.houserent;

import java.util.ArrayList;
import java.util.Scanner;

public class delete {
    // 删除房屋信息
    public static void delete(ArrayList<house> houseArrayList){
        System.out.println("----------------------------------delete----------------------------------");
        System.out.print("Please select the house number to delete (-1 == exit): ");
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        if (str.equals("-1")){
            return;
        }
        house item = hasNumber.hasNumber(houseArrayList, str);
        if (item != null){
            System.out.print("Are you sure?[y/n]  ");
            str = sc.next();
            if (str.equals("y")) {
                houseArrayList.remove(item);
                System.out.println("-----------------------------delete success-------------------------------");
            } else if (str.equals("n")) {
                System.out.println("-----------------------------cancel delete--------------------------------");
            } else {
                System.out.println("Input wrong, try again.");
            }
        }else{
            System.out.println("------------------------------can't find----------------------------------");
        }
    }
}

