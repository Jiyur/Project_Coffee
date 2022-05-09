package com.example.project_login.Activities;

import static com.example.project_login.Activities.CategoryActivity.CATEGORY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_login.Adapter.DrinkAdapter;
import com.example.project_login.DAO.DrinkDAO;
import com.example.project_login.DTO.DrinkDTO;
import com.example.project_login.Dialog.DeleteDrinkDialog;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
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
    final GridView gridView = findViewById(R.id.grdVw_Drink);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(CATEGORY);

        List<DrinkDTO> drinkDTOS = getListData(categoryName);
        gridView.setAdapter(new DrinkAdapter(this, drinkDTOS));

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                DrinkDTO drinkDTO = (DrinkDTO) gridView.getItemAtPosition(position);
                DeleteDrinkDialog deleteDrinkDialog =
                        new DeleteDrinkDialog(DrinkActivity.this, drinkDTO.getDrinkId());
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

    private List<DrinkDTO> getListData(String categoryName) {
        List<DrinkDTO> list = new ArrayList<>();
        DatabaseReference listDrink = DrinkDAO.getMyDrinkDatabase();

        listDrink.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    DrinkDTO drinkDTO = dataSnapshot.getValue(DrinkDTO.class);
                    if(drinkDTO.getDrinkCategory() == categoryName) {
                        list.add(drinkDTO);
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