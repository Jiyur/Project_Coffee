package com.example.project_login.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.project_login.R;
import android.os.Bundle;
import android.widget.GridView;

public class DrinkActivity extends AppCompatActivity {

    private GridView gridView = findViewById(R.id.grdVw_Drink);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
    }
}