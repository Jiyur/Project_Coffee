package com.example.project_login.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Parcelable {
    private String fullName;
    private String phone;
    private String password;
    private String role;
    private String isVerified;
    private String gender;
    private String birth;

    protected User(Parcel in) {
        fullName = in.readString();
        phone = in.readString();
        password = in.readString();
        role = in.readString();
        isVerified = in.readString();
        gender = in.readString();
        birth = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public User(){}
    public User(String fullName, String phone, String password, String role) {
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.gender="";
        this.birth="";
        this.isVerified="No";
    }

    public User(String fullName, String phone, String password, String role, String gender, String birth) {
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.birth = birth;
        this.isVerified = "No";
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fullName);
        parcel.writeString(phone);
        parcel.writeString(password);
        parcel.writeString(role);
        parcel.writeString(isVerified);
        parcel.writeString(gender);
        parcel.writeString(birth);
    }
}
