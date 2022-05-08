package com.example.project_login.DTO;

import java.io.Serializable;

public class DrinkDTO implements Serializable {
    private String id, category, image;
    private int price;

    public DrinkDTO(String drinkId, String drinkImage, int drinkPrice) {
        this.id = drinkId;
        this.image = drinkImage;
        this.price = drinkPrice;
        category = "other";
    }
    public DrinkDTO(String drinkId, String drinkCategory
            , String drinkImage, int drinkPrice) {
        this.id = drinkId;
        this.category = drinkCategory;
        this.image = drinkImage;
        this.price = drinkPrice;
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

    public void setDrinkId(String drinkId) {
        this.id = drinkId;
    }

    public void setDrinkCategory(String drinkCategory) {
        this.category = drinkCategory;
    }

    public void setDrinkImage(String drinkImage) {
        this.image = drinkImage;
    }

    public void setDrinkPrice(int drinkPrice) {
        this.price = drinkPrice;
    }
}
