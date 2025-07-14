package com.houserent;

import java.util.ArrayList;
import java.util.Scanner;

public class update {
    // 更新房屋信息
    public static void update(ArrayList<house> houseArrayList) {
        System.out.println("----------------------------------update----------------------------------");
        System.out.print("Please select the house number to update (-1 == exit): ");
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        sc.nextLine();
        if (str.equals("-1")) {
            return;
        }
        house item = hasNumber.hasNumber(houseArrayList, str);
        if (item != null) {
            System.out.print("number(" + item.getNumber() + "): ");
            str = sc.nextLine();
            if (!str.equals("")) {
                item.setNumber(str);
            }

            System.out.print("name(" + item.getName() + "): ");
            str = sc.nextLine();
            if (!str.equals("")) {
                item.setName(str);
            }

            System.out.print("tel(" + item.getTel() + "): ");
            str = sc.nextLine();
            if (!str.equals("")) {
                item.setTel(str);
            }

            System.out.print("address(" + item.getAddress() + "): ");
            str = sc.nextLine();
            if (!str.equals("")) {
                item.setAddress(str);
            }

            System.out.print("monthPrice(" + item.getMonthPrice() + "): ");
            str = sc.nextLine();
            if (!str.equals("")) {
                item.setMonthPrice(Integer.parseInt(str));
            }

            System.out.print("state(" + item.getState() + "): ");
            str = sc.nextLine();
            if (!str.equals("")) {
                item.setState(str);
            }
        } else {
            System.out.println("------------------------------can't find----------------------------------");
        }
    }
}

