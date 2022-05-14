package com.example.project_login.DTO;

import java.io.Serializable;

public class Category implements Serializable {
    private String img;
    private String catName;

    public Category() { }
    public Category(String img, String catName) {
        this.img = img;
        this.catName = catName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
