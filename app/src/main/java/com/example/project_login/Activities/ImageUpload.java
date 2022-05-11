package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.R;
import com.example.project_login.databinding.ActivityImageUploadBinding;
import com.example.project_login.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageUpload extends Activity {
    ProgressDialog progressDialog;
    private static int REQUEST_CODE=1000;
    ImageView image_upload;
    Button save,upload;
    StorageReference storageReference;
    ActivityImageUploadBinding binding;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityImageUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        image_upload=findViewById(R.id.image_upload);
//        save=findViewById(R.id.set_image_btn);
//        upload=findViewById(R.id.upload_image_btn);

        binding.setImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        binding.uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


    }
    public void selectImage(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(REQUEST_CODE==REQUEST_CODE);
            {
                binding.imageUpload.setImageURI(data.getData());
                imageUri=data.getData();
            }
        }
    }
    public void uploadImage(){
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
                        binding.imageUpload.setImageURI(null);
                        Toast.makeText(ImageUpload.this, "Success upload", Toast.LENGTH_SHORT).show();
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        storageReference.child("images/"+fileName).getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Drinks drinks=new Drinks("coffee","Water",uri.toString(),1234);

                                        Glide.with(getApplicationContext()).load(Uri.parse(drinks.getImage())).into(binding.imageUpload);
                                    }
                                });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(ImageUpload.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}