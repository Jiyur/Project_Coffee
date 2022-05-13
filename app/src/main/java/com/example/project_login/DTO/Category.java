package com.example.project_login.DTO;

import java.io.Serializable;

public class Category implements Serializable {
    private String catId;
    private String catName;

    public Category() { }
    public Category(String catName) {
        this.catName = catName;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
