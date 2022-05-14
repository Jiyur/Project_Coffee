package com.example.project_login.DAO;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project_login.DTO.Bill;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BillDAO {
    public static final String linkConnect = "https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app/";
    public static final DatabaseReference myDatabase = FirebaseDatabase
            .getInstance(linkConnect)
            .getReference("Bill");

    public static void addOrUpdate(String billID, Bill bill) {
        myDatabase.child(billID).setValue(bill);
    }

    public static void delete(String billID, Context context){
        myDatabase.child(billID).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error == null) {
                    //edit successful
                } else {
                    Log.e(TAG, "delete bill - error: " + error);
                }
            }
        });
    }

    public static DatabaseReference getMyDatabase() { return myDatabase; }
}