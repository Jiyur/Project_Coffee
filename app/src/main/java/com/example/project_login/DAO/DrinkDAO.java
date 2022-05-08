package com.example.project_login.DAO;

import com.example.project_login.DTO.DrinkDTO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DrinkDAO {
    private static final DatabaseReference myDrinkDatabase = FirebaseDatabase
            .getInstance("https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("Drinks");

    public static void insertDrink(String id, DrinkDTO drinkDTO) {
        myDrinkDatabase.child(id).setValue(drinkDTO);
    }

    public static void deleteDrink(String id) {
        myDrinkDatabase.child(id).removeValue();
    }

    public static DatabaseReference getMyDrinkDatabase() {
        return myDrinkDatabase;
    }
}
