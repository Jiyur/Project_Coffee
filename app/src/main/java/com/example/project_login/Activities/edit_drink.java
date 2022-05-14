package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_login.DAO.DrinkDAO;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class edit_drink extends AppCompatActivity {
    ProgressDialog progressDialog;
    Uri imageUri;
    StorageReference storageReference;
    TextInputEditText name_txt, price_txt;
    ImageView imageView;
    Button saveBtn;
    DatabaseReference mDatabase;
    Drinks drink;
    private static int REQUEST_CODE=1000;

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

        Glide.with(edit_drink.this).load(drink.getImage()).override(400, 400).centerCrop().into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
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

    public void Save() {
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
//                        Toast.makeText(edit_drink.this, "Success upload", Toast.LENGTH_SHORT).show();
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        storageReference.child("images/"+fileName).getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        DrinkDAO.update(drink.getId(), name_txt.getText().toString()
                                                , Integer.parseInt(price_txt.getText().toString()), uri.toString(),edit_drink.this);
                                    }
                                });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
//                Toast.makeText(edit_drink.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}