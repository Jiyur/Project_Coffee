package com.example.project_login.DTO;

import java.io.Serializable;

public class DrinkDTO implements Serializable {
    private String drinkId, drinkCategory, drinkImage;
    private int drinkPrice;

    public DrinkDTO(String drinkId, String drinkImage, int drinkPrice) {
        this.drinkId = drinkId;
        this.drinkImage = drinkImage;
        this.drinkPrice = drinkPrice;
        drinkCategory = "other";
    }
    public DrinkDTO(String drinkId, String drinkCategory
            , String drinkImage, int drinkPrice) {
        this.drinkId = drinkId;
        this.drinkCategory = drinkCategory;
        this.drinkImage = drinkImage;
        this.drinkPrice = drinkPrice;
    }

    public String getDrinkId() {
        return drinkId;
    }

    public String getDrinkImage() {
        return drinkImage;
    }

    public int getDrinkPrice() {
        return drinkPrice;
    }

    public String getDrinkCategory() {
        return drinkCategory;
    }

    public void setDrinkId(String drinkId) {
        this.drinkId = drinkId;
    }

    public void setDrinkCategory(String drinkCategory) {
        this.drinkCategory = drinkCategory;
    }

    public void setDrinkImage(String drinkImage) {
        this.drinkImage = drinkImage;
    }

    public void setDrinkPrice(int drinkPrice) {
        this.drinkPrice = drinkPrice;
    }
}
