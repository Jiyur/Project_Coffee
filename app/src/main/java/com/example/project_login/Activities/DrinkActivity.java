package com.example.project_login.Activities;

import static com.example.project_login.Activities.CategoryActivity.CATEGORY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_login.Adapter.DrinkAdapter;
import com.example.project_login.DAO.DrinkDAO;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.Dialog.DeleteDrinkDialog;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class DrinkActivity extends AppCompatActivity {

    static final String ACTION = "action";
    GridView gridView;
    DrinkAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        gridView = findViewById(R.id.grdVw_Drink);
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(CATEGORY);
        List<Drinks> drinks=new ArrayList<>();
        myAdapter=new DrinkAdapter(this,drinks);
        gridView.setAdapter(myAdapter);
        DatabaseReference databaseReference=DrinkDAO.getMyDrinkDatabase();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                drinks.clear();
                for(DataSnapshot snap: snapshot.getChildren()){
                    Drinks drink=snap.getValue(Drinks.class);
                    if (drink.getCategory().equals("coffee")){
                        drinks.add(drink);

                    }

                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Drinks drink = (Drinks) gridView.getItemAtPosition(position);
                DeleteDrinkDialog deleteDrinkDialog =
                        new DeleteDrinkDialog(DrinkActivity.this, drink.getId());
                deleteDrinkDialog.show();
                return true;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drink_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_Drink:
                Intent intent = new Intent(DrinkActivity.this, AddDrinkActivity.class);
                intent.putExtra(ACTION, item.getTitle().toString());
                startActivity(intent);
            default:
                return super.onContextItemSelected(item);
        }
    }

    private List<Drinks> getListData(String categoryName) {
        List<Drinks> list = new ArrayList<>();
        DatabaseReference listDrink = DrinkDAO.getMyDrinkDatabase();

        listDrink.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Drinks drink = dataSnapshot.getValue(Drinks.class);
                    if(drink.getCategory() == categoryName) {
                        list.add(drink);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
}