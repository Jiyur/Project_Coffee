package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_login.Adapter.CategoryAdapter;
import com.example.project_login.DAO.CategoryDAO;
import com.example.project_login.DTO.CategoryDTO;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    ListView listView;
    static final String CATEGORY = "category";
    CategoryAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        listView=findViewById(R.id.lstVw_Category);
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        myAdapter=new CategoryAdapter(this,categoryDTOList);
//        listView.setAdapter(new CategoryAdapter(this, categoryDTOList));
        listView.setAdapter(myAdapter);
        DatabaseReference databaseReference=CategoryDAO.getCategory();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryDTOList.clear();
                for(DataSnapshot snap: snapshot.getChildren()){
                    CategoryDTO category=snap.getValue(CategoryDTO.class);
                    Log.e("Cate:",category.getCatName());
                    categoryDTOList.add(category);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CategoryDTO categoryDTO = (CategoryDTO) listView.getItemAtPosition(position);
                String categoryName = categoryDTO.getCatName();
                Intent intent = new Intent(CategoryActivity.this, DrinkActivity.class);
                intent.putExtra(CATEGORY, categoryName);
                startActivity(intent);
            }
        });
//
    }

//
}