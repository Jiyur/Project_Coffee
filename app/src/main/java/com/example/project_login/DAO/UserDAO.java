package com.example.project_login.DAO;

import androidx.annotation.NonNull;

import com.example.project_login.DTO.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDAO{
    private static final DatabaseReference myDatabase= FirebaseDatabase
            .getInstance("https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("users");

    public static void insert(String id, User user){
        myDatabase.child(id).setValue(user);
    }
    public static void delete(String id){
        myDatabase.child(id).removeValue();
    }
    public static void newPassword(String id,String password){
        myDatabase.child(id).child("password").setValue(password);
    }
    public static DatabaseReference getMyDatabase() {
        return myDatabase;
    }
}
