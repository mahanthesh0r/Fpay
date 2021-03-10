package com.mahanthesh.fpay.model;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String gender;
    private String address;

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
}
