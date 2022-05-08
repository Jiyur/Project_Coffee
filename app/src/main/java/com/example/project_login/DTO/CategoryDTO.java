package com.example.project_login.DTO;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
    private String catId;
    private String catName;

    public CategoryDTO() { }
    public CategoryDTO(String catId, String catName) {
        this.catId = catId;
        this.catName = catName;
    }

    public String getCatId() {
        return catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
