package com.houserent;

public class house {
    private String number; // 房子编号
    private String name; // 房主姓名
    private String tel; // 房主电话
    private String address; // 房屋地址
    private int monthPrice; // 月租金
    private String state; // 房屋出租状态 0表示未出租 1表示已出租

    public house() {
    }

    public house(String number, String name, String tel, String address, int monthPrice, String state) {
        this.number = number;
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.monthPrice = monthPrice;
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMonthPrice() {
        return monthPrice;
    }

    public void setMonthPrice(int monthPrice) {
        this.monthPrice = monthPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

