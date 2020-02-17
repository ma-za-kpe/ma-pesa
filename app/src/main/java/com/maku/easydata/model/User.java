package com.maku.easydata.model;

public class User {

    private String phoneNumber;
    private String amount;

    public User(String phoneNumber, String amount) {
        this.phoneNumber = phoneNumber;
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
