package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.project_login.Activities.Table.table_management;
import com.example.project_login.Adapter.MenuCategoryAdapter;
import com.example.project_login.DAO.CategoryDAO;
import com.example.project_login.DAO.TableDAO;
import com.example.project_login.DTO.Category;
import com.example.project_login.DTO.Table;
import com.example.project_login.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class menu_category extends AppCompatActivity {
    ListView list_category;
    List<Category> listCat;
    MenuCategoryAdapter menuCategoryAdapter;
    DatabaseReference mDatabase;
    Toolbar toolbar;
    Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_category);

        list_category = findViewById(R.id.list_category);
        mDatabase = CategoryDAO.getMyDatabase();
        toolbar = findViewById(R.id.list_category_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data();
        list_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = (Category) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(menu_category.this, drink_by_category.class);
                intent.putExtra("Category", category.getCatName());
                startActivity(intent);
            }
        });
    }

    public void data(){
        listCat = new ArrayList<Category>();
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Category category = (Category) snapshot.getValue(Category.class);
                listCat.add(category);
                menuCategoryAdapter = new MenuCategoryAdapter(menu_category.this, R.layout.menu_category_item, listCat);
                list_category.setAdapter(menuCategoryAdapter);
                registerForContextMenu(list_category);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

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
//                Intent intent = new Intent(menu_category.this, HomePageActivity.class);
//                startActivity(intent);
//                finish();
                onBackPressed();
                break;
            case R.id.add_item:
                add_category();
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void add_category(){

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
        category = (Category) menuCategoryAdapter.getItem(pos);
        switch (item.getItemId()){
            case R.id.delete_item:
                TableDAO.delete(category.getCatName(), menu_category.this);
                break;
            case R.id.edit_item:
                editCategory(Gravity.CENTER);
                break;
            default:break;
        }
        return super.onContextItemSelected(item);
    }

    public void editCategory(int center){}
}