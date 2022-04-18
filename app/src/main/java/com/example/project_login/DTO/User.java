package com.example.project_login.DTO;

import java.io.Serializable;

public class User implements Serializable {
    private String fullName;
    private String phone;
    private String password;
    private String role;
    private String isVerified;
    public User(){}
    public User(String fullName, String phone, String password, String role) {
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.isVerified="No";
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }
}
