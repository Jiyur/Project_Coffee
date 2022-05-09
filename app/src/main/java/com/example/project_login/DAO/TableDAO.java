package com.example.project_login.DAO;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.project_login.Activities.add_staff;
import com.example.project_login.Activities.edit_staff;
import com.example.project_login.Activities.table_management;
import com.example.project_login.DTO.Table;
import com.example.project_login.DTO.User;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TableDAO {
    private static final DatabaseReference myDatabase= FirebaseDatabase
            .getInstance("https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("tables");

    public static void insert(Context context){
        Table table = new Table("no");
        myDatabase.push().setValue(table, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null){
                    Toast.makeText(context, "Add table success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Add table fail", Toast.LENGTH_SHORT).show();
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
    public static void update(Table table, Context context){
        HashMap<String, Object> postValues = new HashMap<>();
        postValues.put(table.getIdTable()+"/idBill", table.getIdBill());
        postValues.put(table.getIdTable()+"/status", table.getStatus());
        myDatabase.updateChildren(postValues, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error == null){
                    Toast.makeText(context, "Edit table success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Edit table fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static DatabaseReference getMyDatabase() {
        return myDatabase;
    }
}
