package com.mahanthesh.fpay.model;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String gender;
    private String address;
    private Integer wallet_balance;

    public UserInfo() {
    }

    public UserInfo(String firstName, String lastName, String email, String phoneNo, String gender, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.address = address;
    }

    /*
        Getters method
     */

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public Integer getWallet_balance() {
        return wallet_balance;
    }

    /*
    Setters method
     */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWallet_balance(Integer wallet_balance) {
        this.wallet_balance = wallet_balance;
    }
}
