package com.houserent;

import java.util.ArrayList;
import java.util.Scanner;

public class houseRent {
    public static void main(String[] args) {
        ArrayList<house> houseArrayList = new ArrayList<>();
        boolean loop = true;
        Scanner sc = new Scanner(System.in);
        do{
            menu.menu();
            String str = sc.nextLine();
            switch (str) {
                case "1" -> add.add(houseArrayList);
                case "2" -> find.find(houseArrayList);
                case "3" -> delete.delete(houseArrayList);
                case "4" -> update.update(houseArrayList);
                case "5" -> view.view(houseArrayList);
                case "6" -> loop = exit.exit();
                default -> System.out.println("输入错误");
            }
        }while(loop);
    }
}

