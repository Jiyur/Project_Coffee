package com.example.project_login.Activities;

import static com.example.project_login.Activities.DrinkActivity.ACTION;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_login.DAO.DrinkDAO;
import com.example.project_login.DTO.DrinkDTO;
import com.example.project_login.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class AddDrinkActivity extends Activity {

    private final int REQ_CODE = 1000;

    final EditText editTextDrinkName = findViewById(R.id.edtTxt_DrinkName);
    final Spinner spinnerCategory = findViewById(R.id.spn_DrinkCategory);
    final EditText editTextPrice = findViewById(R.id.edtTxt_DrinkPrice);
    final ImageView imageViewDrinkImage = findViewById(R.id.imgVw_DrinkImage);
    final Button buttonAddDrink = findViewById(R.id.btn_AddDrink);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        Intent intent = getIntent();
        String action = intent.getStringExtra(ACTION);

        if(action == "add") {
            buttonAddDrink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String drinkName = editTextDrinkName.getText().toString().trim();
                    String drinkCategory = spinnerCategory.getSelectedItem().toString();
                    String drinkPrice = editTextPrice.getText().toString();

                    if (drinkName.isEmpty() || drinkPrice.trim().isEmpty()) {
                        Toast.makeText(AddDrinkActivity.this, "Trường không được để trống"
                                , Toast.LENGTH_SHORT).show();
                    } else if (drinkCategory.isEmpty()) {
                        String category = "other";

                        DrinkDTO drinkDTO = new DrinkDTO(drinkName, category, "", Integer.parseInt(drinkPrice));
                        DrinkDAO drinkDAO = new DrinkDAO();

                        drinkDAO.insertDrink(drinkName, drinkDTO);

                        Toast.makeText(AddDrinkActivity.this, "Đã thêm thành công"
                                , Toast.LENGTH_SHORT).show();
                    } else {
                        DrinkDTO drinkDTO = new DrinkDTO(drinkName, drinkCategory, "", Integer.parseInt(drinkPrice));
                        DrinkDAO drinkDAO = new DrinkDAO();

                        drinkDAO.insertDrink(drinkName, drinkDTO);

                        Toast.makeText(AddDrinkActivity.this, "Đã thêm thành công"
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            });

            imageViewDrinkImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentImage = new Intent(Intent.ACTION_PICK);
                    intentImage.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intentImage, REQ_CODE);
                }
            });
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == REQ_CODE) {
                imageViewDrinkImage.setImageURI(data.getData());
            }
        }
    }
}