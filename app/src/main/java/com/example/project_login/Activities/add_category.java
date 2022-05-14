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

import com.example.project_login.DAO.CategoryDAO;
import com.example.project_login.DAO.DrinkDAO;
import com.example.project_login.DTO.Category;
import com.example.project_login.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class add_category extends AppCompatActivity {
    ImageView imageView;
    TextInputEditText category_txt;
    Button add_btn;
    ProgressDialog progressDialog;
    Uri imageUri;
    StorageReference storageReference;
    private static int REQUEST_CODE=1000;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        imageView = findViewById(R.id.category_img);
        category_txt = findViewById(R.id.textInputEditText_category);
        add_btn = findViewById(R.id.add_btn);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
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

    public void add(){
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
                                        category = new Category(uri.toString(), category_txt.getText().toString());
                                        CategoryDAO.insert(category, add_category.this);
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