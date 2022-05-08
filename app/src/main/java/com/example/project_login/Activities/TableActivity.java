package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_login.Adapter.TableAdapter;
import com.example.project_login.DAO.TableDAO;
import com.example.project_login.DTO.Table;
import com.example.project_login.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class TableActivity extends AppCompatActivity {
    Button buttonXoaBan, buttonThemBan;
    GridView gvTable;
    TextView tx;
    ArrayList<Table> tableList;
    TableAdapter adapter;
    public static Table tableData = new Table();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        gvTable = (GridView) findViewById(R.id.gvTable);
        final TextView tx=findViewById(R.id.textView2);
        LoadData();
//        adapter = new TableAdapter(this,R.layout.layouttable,tableList);
//        gvTable.setAdapter(adapter);

    }

    private void getDataTable(){
        tableList.add(new Table("Ban 1","Free","none"));
        tableList.add(new Table("Ban 2","Free","none"));
        tableList.add(new Table("Ban 3","Free","none"));
        tableList.add(new Table("Ban 4","Busy","none"));
        tableList.add(new Table("Ban 5","Free","none"));
    }
    private void LoadData() {
        tableList = new ArrayList<>();
        DatabaseReference myDatabase = TableDAO.getMyDatabase();
        myDatabase.setValue(new Table("1","2","3"));



    }


}