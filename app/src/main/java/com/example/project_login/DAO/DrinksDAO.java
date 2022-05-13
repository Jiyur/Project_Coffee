package com.example.project_login.DAO;

import com.example.project_login.DTO.Bill;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DrinksDAO {
    public static final String linkConnect = "https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app/";
    public static final DatabaseReference myDatabase = FirebaseDatabase
            .getInstance(linkConnect)
            .getReference("Drinks");

    public static DatabaseReference getMyDatabase() { return myDatabase; }
}