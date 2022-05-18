package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project_login.DAO.DrinkDAO;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class add_drink extends AppCompatActivity {
    ProgressDialog progressDialog;
    TextInputEditText name_txt, price_txt, category_txt;
    ImageView imageView;
    Button addBtn;
    Toolbar toolbar;
    FloatingActionButton addImageBTN;
    private static int REQUEST_CODE=1000;
    Uri imageUri;
    Drinks drink;
    DatabaseReference mDatabase;
    StorageReference storageReference;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);
        name_txt = findViewById(R.id.textInputEditText_name);
        price_txt = findViewById(R.id.textInputEditText_price);
        category_txt = findViewById(R.id.textInputEditText_category);
        addBtn = findViewById(R.id.add_btn);
        imageView = findViewById(R.id.drink_img);
        addImageBTN=findViewById(R.id.add_drink_btn);
        category_txt.setText(getIntent().getStringExtra("Category"));
        category_txt.setFocusable(false);
        mDatabase = DrinkDAO.getMyDatabase();
        toolbar = findViewById(R.id.addDrink_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addImageBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(REQUEST_CODE==REQUEST_CODE);
            {
                imageView.setImageURI(data.getData());
                imageUri=data.getData();
            }
        }
    }

    public void Add(){
        if(imageUri == null || name_txt.equals("") || price_txt.equals("")){
            Toast.makeText(this, "You must fill in all the information before adding", Toast.LENGTH_SHORT).show();
        }else{
            progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading ...");
            progressDialog.show();
            SimpleDateFormat format=new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.ENGLISH);
            Date now =new Date();
            String fileName=format.format(now);
            storageReference= FirebaseStorage.getInstance().getReference();
            storageReference.child("images/"+fileName)
                    .putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageView.setImageURI(null);
                            Toast.makeText(add_drink.this, "Success upload", Toast.LENGTH_SHORT).show();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            storageReference.child("images/"+fileName).getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            drink = new Drinks(category_txt.getText().toString(), name_txt.getText().toString(),
                                                    uri.toString(), Integer.parseInt(price_txt.getText().toString()));
                                            DrinkDAO.insert(add_drink.this, drink);
                                            add_drink.super.onBackPressed();
                                        }
                                    });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(add_drink.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}