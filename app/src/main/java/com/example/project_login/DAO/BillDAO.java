package com.example.project_login.DAO;

import com.example.project_login.DTO.Bill;
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

    public static DatabaseReference getMyDatabase() { return myDatabase; }
}