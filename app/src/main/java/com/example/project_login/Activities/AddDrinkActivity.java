package com.example.project_login.Activities;

import static com.example.project_login.Activities.DrinkActivity.ACTION;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project_login.DAO.DrinkDAO;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.R;

public class AddDrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        final EditText editTextDrinkName = findViewById(R.id.edtTxt_DrinkName);
        final EditText editTextCategory = findViewById(R.id.edtTxt_DrinkCategory);
        final EditText editTextPrice = findViewById(R.id.edtTxt_DrinkPrice);
        final ImageView imageViewDrinkImage = findViewById(R.id.imgVw_DrinkImage);
        final Button buttonAddDrink = findViewById(R.id.btn_AddDrink);

        Intent intent = getIntent();
        String action = intent.getStringExtra(ACTION);

        if(action == "add") {
            buttonAddDrink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String drinkName = editTextDrinkName.getText().toString().trim();
                    String drinkCategory = editTextCategory.getText().toString().trim();
                    String drinkPrice = editTextPrice.getText().toString();

                    if (drinkName.isEmpty() || drinkPrice.trim().isEmpty()) {
                        Toast.makeText(AddDrinkActivity.this, "Trường không được để trống"
                                , Toast.LENGTH_SHORT).show();
                    } else if (drinkCategory.isEmpty()) {
                        String category = "other";

                        Drinks drink = new Drinks(drinkName, category, "", Integer.parseInt(drinkPrice));
                        DrinkDAO drinkDAO = new DrinkDAO();

                        drinkDAO.insertDrink(drinkName, drink);

                        Toast.makeText(AddDrinkActivity.this, "Đã thêm thành công"
                                , Toast.LENGTH_SHORT).show();
                    } else {
                        Drinks drink = new Drinks(drinkName, drinkCategory, "", Integer.parseInt(drinkPrice));
                        DrinkDAO drinkDAO = new DrinkDAO();

                        drinkDAO.insertDrink(drinkName, drink);

                        Toast.makeText(AddDrinkActivity.this, "Đã thêm thành công"
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            });

            imageViewDrinkImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        } else {
            finish();
        }
    }
}