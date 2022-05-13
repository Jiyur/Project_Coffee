package com.example.project_login.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;

public class edit_drink extends AppCompatActivity {
    TextInputEditText name_txt, price_txt;
    ImageView imageView;
    Button saveBtn;
    DatabaseReference mDatabase;
    Drinks drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drink);

        name_txt = findViewById(R.id.textInputEditText_name);
        price_txt = findViewById(R.id.textInputEditText_price);
        saveBtn = findViewById(R.id.save_btn);
        imageView = findViewById(R.id.drink_img);
        Bundle bundle = getIntent().getExtras();
        drink = (Drinks) bundle.getParcelable("Drink");
        name_txt.setText(drink.getName());
        price_txt.setText(String.valueOf(drink.getPrice()));

        Glide.with(edit_drink.this).load(drink.getImage()).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}