package com.example.project_login.DTO;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
    private String catName;

    public CategoryDTO() { }
    public CategoryDTO(String catName) {
        this.catName = catName;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
