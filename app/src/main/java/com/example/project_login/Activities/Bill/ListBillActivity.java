package com.example.project_login.Activities.Bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_login.DAO.BillDAO;
import com.example.project_login.DTO.Bill;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ListBillActivity extends AppCompatActivity {

    TextView txtTittle;
    ListView lvBill;

    public static Bill billData = new Bill();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bill);

        Init();
        LoadData();
        AddEvents();
    }

    private void AddEvents() {
        lvBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                String item = (String) lvBill.getAdapter().getItem(pos);
                String billID = "";

                billID = item;
                Intent intent = new Intent(ListBillActivity.this, BillActivity.class);
                Bundle b = new Bundle();
                b.putString("tableID/billID", "/"  + billID);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void LoadData() {
        DatabaseReference myDatabase = BillDAO.getMyDatabase();
        myDatabase.addValueEventListener(new ValueEventListener() {
            ArrayAdapter<String> arrayAdapter;
            ArrayList<Bill> arrayListBill = new ArrayList<Bill>();
            ArrayList<String> arrayListItem = new ArrayList<String>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListBill.clear();
                arrayListItem.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    billData = postSnapshot.getValue(Bill.class);
                    arrayListBill.add(billData);
                }

                Collections.sort(arrayListBill, new Bill());

                for(int i=0; i<arrayListBill.size(); ++i){
                    arrayListItem.add(arrayListBill.get(i).getId());
                }

                arrayAdapter = new ArrayAdapter<String>(ListBillActivity.this, android.R.layout.simple_list_item_1, arrayListItem);

                lvBill.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListBillActivity.this, "Check your connect!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Init(){
        txtTittle = (TextView) findViewById(R.id.textviewtittle);
        lvBill = (ListView) findViewById(R.id.listviewBill);
    }

    public void btnbBack_Click(View view) { finish(); }
}