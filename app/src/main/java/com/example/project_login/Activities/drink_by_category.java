package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_login.Activities.Bill.BillActivity;
import com.example.project_login.Activities.Order.OrderActivity;
import com.example.project_login.Activities.Table.table_management;
import com.example.project_login.Adapter.DrinkAdapter;
import com.example.project_login.Adapter.TableAdapter;
import com.example.project_login.DAO.BillDAO;
import com.example.project_login.DAO.DrinkDAO;
import com.example.project_login.DAO.TableDAO;
import com.example.project_login.DTO.Bill;
import com.example.project_login.DTO.Category;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.DTO.Table;
import com.example.project_login.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class drink_by_category extends AppCompatActivity {
    GridView gridView;
    Toolbar toolbar;
    DatabaseReference mDatabase;
    List<Drinks> listDrink;
    DrinkAdapter drinkAdapter;
    Drinks drink;
    String category;
    String tableId = "";
    String tableName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_by_category);
        mDatabase = DrinkDAO.getMyDatabase();
        toolbar = findViewById(R.id.listDrink_toolbar);
        gridView = findViewById(R.id.drink_gridView);
        tableId = getIntent().getStringExtra("tableID");
        tableName = getIntent().getStringExtra("tableName");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(Global.check){
                    Drinks drink = (Drinks) adapterView.getAdapter().getItem(i);
                    Intent intent = new Intent(drink_by_category.this, com.example.project_login.Activities.Order.OrderActivity.class);
                    intent.putExtra("tableID", tableId);
                    intent.putExtra("tableName", tableName);
                    intent.putExtra("drinkID", drink.getId());
                    startActivity(intent);
                }
            }
        });

        data();
    }

    public void data(){
        listDrink = new ArrayList<Drinks>();
        category = getIntent().getStringExtra("Category");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                HashMap<String, Object> postValues = new HashMap<>();
                postValues.put(snapshot.getKey()+"/id", snapshot.getKey());
                mDatabase.updateChildren(postValues);
                Drinks drink = (Drinks) snapshot.getValue(Drinks.class);
                drink.setId(snapshot.getKey().toString());
                if(drink.getCategory().toString().equals(category)){
                    listDrink.add(drink);
                    drinkAdapter = new DrinkAdapter(drink_by_category.this, R.layout.list_drink_item, listDrink);
                    gridView.setAdapter(drinkAdapter);
                }
                registerForContextMenu(gridView);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Drinks drink = snapshot.getValue(Drinks.class);
                if(drink == null || listDrink == null || listDrink.isEmpty()){
                    return;
                }
                for(int i = 0; i < listDrink.size(); i++){
                    if(drink.getId().toString().equals(listDrink.get(i).getId().toString())){
                        listDrink.remove(listDrink.get(i));
                        break;
                    }
                }
                drinkAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_option_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
//                Intent intent = new Intent(drink_by_category.this, menu_category.class);
//                startActivity(intent);
//                finish();
                onBackPressed();
                break;
            case R.id.add_item:
                Intent intent = new Intent(drink_by_category.this, add_drink.class);
                intent.putExtra("Category", category);
                startActivity(intent);
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context_table, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        drink =  (Drinks) drinkAdapter.getItem(pos);
        switch (item.getItemId()){
            case R.id.delete_item:
                DrinkDAO.delete(drink.getId(), drink_by_category.this);
                break;
            case R.id.edit_item:
                Intent intent = new Intent(drink_by_category.this, edit_drink.class);
                intent.putExtra("Category", category);
                intent.putExtra("Drink", drink);
                startActivity(intent);
                break;
            default:break;
        }
        return super.onContextItemSelected(item);
    }
}