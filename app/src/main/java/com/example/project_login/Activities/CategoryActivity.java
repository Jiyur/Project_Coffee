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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    public static final int CATEGORY_COUNT = 4;
    final ListView listView = findViewById(R.id.lstVw_Category);
    static final String CATEGORY = "category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        List<CategoryDTO> categoryDTOList = getListData();
        listView.setAdapter(new CategoryAdapter(this, categoryDTOList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CategoryDTO categoryDTO = (CategoryDTO) listView.getItemAtPosition(position);
                Intent intent = new Intent(CategoryActivity.this, DrinkActivity.class);
                intent.putExtra(CATEGORY, categoryDTO.getCatName());
                startActivity(intent);
            }
        });
    }

    private List<CategoryDTO> getListData() {
        List<CategoryDTO> list = new ArrayList<>();
        DatabaseReference listCategory = CategoryDAO.getCategory();

        listCategory.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int count = CATEGORY_COUNT; count >= 0; count--)
                {
                    CategoryDTO categoryDTO = snapshot.getValue(CategoryDTO.class);
                    list.add(categoryDTO);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
}