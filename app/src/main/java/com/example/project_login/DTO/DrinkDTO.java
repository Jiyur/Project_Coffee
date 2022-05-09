package com.example.project_login.DTO;

import java.io.Serializable;

public class DrinkDTO implements Serializable {
    private String id, category, image;
    private int price;

    public DrinkDTO(String id, String image, int price) {
        this.id = id;
        this.image = image;
        this.price = price;
        category = "other";
    }
    public DrinkDTO(String id, String category
            , String image, int price) {
        this.id = id;
        this.category = category;
        this.image = image;
        this.price = price;
    }

    public String getDrinkId() {
        return id;
    }

    public String getDrinkImage() {
        return image;
    }

    public int getDrinkPrice() {
        return price;
    }

    public String getDrinkCategory() {
        return category;
    }

    public void setDrinkId(String id) {
        this.id = id;
    }

    public void setDrinkCategory(String category) {
        this.category = category;
    }

    public void setDrinkImage(String image) {
        this.image = image;
    }

    public void setDrinkPrice(int price) {
        this.price = price;
    }
}
