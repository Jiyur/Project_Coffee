package com.example.project_login.DAO;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.project_login.DTO.Category;
import com.example.project_login.DTO.Table;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryDAO {
    private static final DatabaseReference myDatabase= FirebaseDatabase
            .getInstance("https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("category");
    public static void insert(){
        Category category = new Category("Coffe");
        myDatabase.push().setValue(category);
    }
    public static List<Category> getCategory(){
        List<Category> listCat = new ArrayList<Category>();
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Category category = (Category) snapshot.getValue(Category.class);
               listCat.add(category);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listCat;
    }

    public static DatabaseReference getMyDatabase() {
        return myDatabase;
    }
}
