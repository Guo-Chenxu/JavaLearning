package com.houserent;

import java.util.ArrayList;
import java.util.Scanner;

public class find {
    // 查找房屋
    public static void find(ArrayList<house> houseArrayList) {
        System.out.println("----------------------------------find-----------------------------------");
        System.out.print("Please input house number: ");
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        house item = hasNumber.hasNumber(houseArrayList, str);
        if (item != null) {
            System.out.print((String.format("%-12s", item.getNumber())));
            System.out.print((String.format("%-12s", item.getName())));
            System.out.print((String.format("%-12s", item.getTel())));
            System.out.print((String.format("%-12s", item.getAddress())));
            System.out.print((String.format("%-12s", item.getMonthPrice())));
            System.out.print((String.format("%-12s", item.getState())));
            System.out.println("\n------------------------------find success-------------------------------");
        } else {
            System.out.println("------------------------------can't find----------------------------------");
        }
    }
}

