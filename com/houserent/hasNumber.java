package com.houserent;

import java.util.ArrayList;

public class hasNumber {
    // 查找是否存在指定编号的房屋
    public static house hasNumber(ArrayList<house> houseArrayList, String str){
        for (house item : houseArrayList){
            if (item.getNumber().equals(str)){
                return item;
            }
        }
        return null;
    }
}

