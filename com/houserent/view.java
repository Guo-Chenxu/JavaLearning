package com.houserent;

import java.util.ArrayList;

public class view {
    // 查看所有房屋
    public static void view(ArrayList<house> houseArrayList) {
        System.out.println("----------------------------------view-----------------------------------");
        System.out.print((String.format("%-12s", "number")));
        System.out.print((String.format("%-12s", "name")));
        System.out.print((String.format("%-12s", "tel")));
        System.out.print((String.format("%-12s", "address")));
        System.out.print((String.format("%-12s", "monthPrice")));
        System.out.print((String.format("%-12s", "state(0/1)")));
        System.out.println();
        for (house item : houseArrayList) {
            System.out.print((String.format("%-12s", item.getNumber())));
            System.out.print((String.format("%-12s", item.getName())));
            System.out.print((String.format("%-12s", item.getTel())));
            System.out.print((String.format("%-12s", item.getAddress())));
            System.out.print((String.format("%-12s", item.getMonthPrice())));
            System.out.print((String.format("%-12s", item.getState())));
            System.out.println();
        }
        System.out.println("\n------------------------------view success-------------------------------");
    }
}

