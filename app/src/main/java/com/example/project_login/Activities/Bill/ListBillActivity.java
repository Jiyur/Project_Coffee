package com.example.project_login.Activities.Bill;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_login.Adapter.ItemInfoAdapter;
import com.example.project_login.DAO.BillDAO;
import com.example.project_login.DTO.Bill;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ListBillActivity extends AppCompatActivity {

    TextView txtTittle;
    ListView lvBill;
    Toolbar toolbar;
    ItemInfoAdapter myAdapter;
    ArrayList<Bill> arrayListBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bill);
        txtTittle = (TextView) findViewById(R.id.textviewtittle);
        lvBill = (ListView) findViewById(R.id.listviewBillInfo);
        arrayListBill=new ArrayList<Bill>();
        myAdapter=new ItemInfoAdapter(this,R.layout.bill_info_layout,arrayListBill);
        lvBill.setAdapter(myAdapter);
        toolbar = findViewById(R.id.listBill_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bill bill=(Bill) lvBill.getItemAtPosition(position);
                Intent intent = new Intent(ListBillActivity.this, BillActivity.class);
                Bundle b = new Bundle();
                b.putString("tableID/billID", "/"  + bill.getId());
                intent.putExtra("bill", "bill");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        DatabaseReference myDatabase = FirebaseDatabase
                .getInstance("https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Bill");
        myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListBill.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Bill billData = postSnapshot.getValue(Bill.class);
                    arrayListBill.add(billData);
                    Log.e("bill",billData.getId());
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}