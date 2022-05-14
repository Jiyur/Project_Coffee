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
    public static void insert(Category category, Context context){
        myDatabase.child(category.getCatName()).setValue(category, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null){
                    Toast.makeText(context, "Add category success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Add category fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public static void delete(String id, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDatabase.child(id).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if(error == null){
                            Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT ).show();
                        }else{
                            Toast.makeText(context, "Delete fail", Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Cancel delete", Toast.LENGTH_SHORT ).show();
            }
        });
        builder.create().show();
    }

    public static DatabaseReference getMyDatabase() {
        return myDatabase;
    }
}
