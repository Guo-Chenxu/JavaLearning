package com.houserent;

import java.util.ArrayList;
import java.util.Scanner;

public class add {
    // 添加新房屋
    public static void add(ArrayList<house> houseArrayList) {
        house newHouse = new house();
        System.out.println("----------------------------------add------------------------------------");
        Scanner sc = new Scanner(System.in);

        System.out.print("number: ");
        String str = sc.next();
        while (hasNumber.hasNumber(houseArrayList, str) != null){
            System.out.println("Number has existed, please try again.");
            System.out.print("number: ");
            str = sc.next();
        }
        newHouse.setNumber(str);

        System.out.print("name: ");
        newHouse.setName(sc.next());
        System.out.print("tel: ");
        newHouse.setTel(sc.next());
        System.out.print("address: ");
        newHouse.setAddress(sc.next());

        System.out.print("monthPrice: ");
        int price = sc.nextInt();
        while (price <= 0){
            System.out.println("Input wrong, try again.");
            System.out.print("monthPrice: ");
            price = sc.nextInt();
        }
        newHouse.setMonthPrice(price);

        System.out.print("state(0/1): ");
        str = sc.next();
        while (!str.equals("0") && !str.equals("1")){
            System.out.println("Input wrong, try again.");
            System.out.print("state(0/1): ");
            str = sc.next();
        }
        newHouse.setState(str);

        houseArrayList.add(newHouse);
        System.out.println("\n----------------------------add success----------------------------------");
    }
}

