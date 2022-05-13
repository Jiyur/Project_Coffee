package com.example.project_login.Activities.Order;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project_login.Activities.Bill.BillActivity;
import com.example.project_login.Activities.Table.table_management;
import com.example.project_login.Adapter.ItemBillAdapter;
import com.example.project_login.Adapter.ItemMenuOrderAdapter;
import com.example.project_login.DAO.BillDAO;
import com.example.project_login.DAO.DrinksDAO;
import com.example.project_login.DTO.Bill;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuOrderActivity extends AppCompatActivity {

    ListView lvMenu;
    Button btnAll, btnJuice, btnCoffee, btnTea, btnOther;
    ItemMenuOrderAdapter itemMenuOrderAdapter;

    ArrayList<Drinks> arrayListDrinks = new ArrayList<Drinks>();

    ArrayList<Drinks> arrayListDrinksCoffee = new ArrayList<Drinks>();
    ArrayList<Drinks> arrayListDrinksJuice = new ArrayList<Drinks>();
    ArrayList<Drinks> arrayListDrinksTea = new ArrayList<Drinks>();
    ArrayList<Drinks> arrayListDrinksOther = new ArrayList<Drinks>();

    String tableID = "", tableName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_order);

        Init();
        LoadData();
        AddEvents();
    }

    private void AddEvents() {
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemMenuOrderAdapter = new ItemMenuOrderAdapter(MenuOrderActivity.this, arrayListDrinks);
                lvMenu.setAdapter(itemMenuOrderAdapter);
            }
        });

        btnCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemMenuOrderAdapter = new ItemMenuOrderAdapter(MenuOrderActivity.this, arrayListDrinksCoffee);
                lvMenu.setAdapter(itemMenuOrderAdapter);
            }
        });

        btnJuice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemMenuOrderAdapter = new ItemMenuOrderAdapter(MenuOrderActivity.this, arrayListDrinksJuice);
                lvMenu.setAdapter(itemMenuOrderAdapter);
            }
        });

        btnTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemMenuOrderAdapter = new ItemMenuOrderAdapter(MenuOrderActivity.this, arrayListDrinksTea);
                lvMenu.setAdapter(itemMenuOrderAdapter);
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemMenuOrderAdapter = new ItemMenuOrderAdapter(MenuOrderActivity.this, arrayListDrinksOther);
                lvMenu.setAdapter(itemMenuOrderAdapter);
            }
        });
    }


    private void LoadData() {
        DatabaseReference myDatabase = DrinksDAO.getMyDatabase();
        myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Drinks drinks;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    drinks = dataSnapshot.getValue(Drinks.class);
                    arrayListDrinks.add(drinks);

                    if(drinks.getCategory().equals("other"))
                        arrayListDrinksOther.add(drinks);
                    if(drinks.getCategory().equals("coffee"))
                        arrayListDrinksCoffee.add(drinks);
                    if(drinks.getCategory().equals("juice"))
                        arrayListDrinksJuice.add(drinks);
                    if(drinks.getCategory().equals("tea"))
                        arrayListDrinksTea.add(drinks);
                }

                itemMenuOrderAdapter = new ItemMenuOrderAdapter(MenuOrderActivity.this, arrayListDrinks);

                lvMenu.setAdapter(itemMenuOrderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public void Init(){
        lvMenu = (ListView) findViewById(R.id.listviewmenu);
        btnAll = (Button) findViewById(R.id.buttonall);
        btnJuice = (Button) findViewById(R.id.buttonjuice);
        btnOther = (Button) findViewById(R.id.buttonother);
        btnTea = (Button) findViewById(R.id.buttontea);
        btnCoffee = (Button) findViewById(R.id.buttoncoffee);

        Bundle b = getIntent().getExtras();
        String temp = null;
        if(b != null)
            temp = b.getString("tableID/tableName");
        boolean change = false;
        for(int i=0; i<temp.length(); ++i){
            if(temp.charAt(i) == '/'){
                change=true;
                continue;
            }
            if(change){
                tableName = tableName + temp.charAt(i);
            } else {
                tableID = tableID + temp.charAt(i);
            }
        }
    }

    public void OpenOrderACtivity(String drinksID){
        Intent intent = new Intent(MenuOrderActivity.this, OrderActivity.class);
        Bundle b = new Bundle();
        b.putString("tableID/tableName/drinksID", tableID + "/" + tableName + "/" +drinksID);
        intent.putExtras(b);
        startActivity(intent);
    }
}