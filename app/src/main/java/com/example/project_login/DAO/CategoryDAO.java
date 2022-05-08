package com.example.project_login.DAO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CategoryDAO {
    private static final DatabaseReference myCategory = FirebaseDatabase
            .getInstance("https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("category");
    public static DatabaseReference getCategory() {
        return myCategory;
    }
}
