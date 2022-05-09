package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_login.Adapter.TableAdapter;
import com.example.project_login.DAO.TableDAO;
import com.example.project_login.DTO.Table;
import com.example.project_login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class TableActivity extends AppCompatActivity {
    ImageView imgviewThemBan;
    GridView gvTable;
    TextView tx;
    ArrayList<Table> tableList;
    TableAdapter adapter;
    Context context;
    public static Table tableData = new Table();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        gvTable = (GridView) findViewById(R.id.gvTable);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogThemBan();
            }
        });
        final TextView tx=findViewById(R.id.textView2);
        LoadData();

    }

    private void DialogThemBan(){
        Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.layoutdialogaddtable);
        dialog.show();
    }
    private void getDataTable(){
        tableList.add(new Table("Ban 1","Free","none"));
        tableList.add(new Table("Ban 2","Free","none"));
        tableList.add(new Table("Ban 3","Free","none"));
        tableList.add(new Table("Ban 4","Busy","none"));
        tableList.add(new Table("Ban 5","Free","none"));
    }
    private void LoadData() {
//        FirebaseDatabase database= FirebaseDatabase.getInstance();
//        DatabaseReference myRel = database.getReference("table");
        tableList = new ArrayList<>();

        DatabaseReference myDatabase = TableDAO.getMyDatabase();
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Table value =  dataSnapshot.getValue(Table.class);
                    tableList.add(value);
                }
                //Log.d("3", tableList.get(1).);
                adapter = new TableAdapter(TableActivity.this,R.layout.layouttable,tableList);
                gvTable.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("OK", "Fasle: " );
            }
        });

    }


}