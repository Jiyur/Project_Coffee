package com.example.project_login.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_login.DTO.CategoryDTO;
import com.example.project_login.R;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoryActivity extends AppCompatActivity {

    ListView listView = findViewById(R.id.lstVw_Category);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ArrayAdapter<CategoryDTO> arrayAdapter = new ArrayAdapter<CategoryDTO>(this, )
    }
}