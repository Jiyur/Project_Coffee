package com.example.project_login.DAO;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.project_login.Adapter.DrinkAdapter;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.DTO.Table;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrinkDAO {
    private static final DatabaseReference myDatabase= FirebaseDatabase
            .getInstance("https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("Drinks");

    public static void getDrinkByCategory(Context context, String catName, GridView gridView){
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Drinks drink;
                List<Drinks> drinksList = new ArrayList<Drinks>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    drink = (Drinks) dataSnapshot.getValue(Drinks.class);
                    if(drink.getCategory().toString().equals(catName)){
                        drinksList.add(drink);
                    }
                }
                DrinkAdapter drinkAdapter = new DrinkAdapter(context, R.layout.list_drink_item, drinksList);
                gridView.setAdapter(drinkAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void insert(Context context, Drinks drink){
        myDatabase.push().setValue(drink, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null){
                    Toast.makeText(context, "Add drink success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Add drink fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
    public static void update(String id, String name, Integer price,String uri, Context context){
        HashMap<String, Object> postValues = new HashMap<>();
        postValues.put(id+"/image", uri);
        postValues.put(id+"/price", price);
        postValues.put(id+"/name", name);
        myDatabase.updateChildren(postValues, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error == null){
                    Toast.makeText(context, "Edit Drink success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Edit Drink fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static DatabaseReference getMyDatabase() {
        return myDatabase;
    }
}
