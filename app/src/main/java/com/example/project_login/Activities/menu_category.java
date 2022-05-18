package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project_login.Activities.Order.MenuOrderActivity;
import com.example.project_login.Activities.Order.OrderActivity;
import com.example.project_login.Activities.StaffManagement.management_staff;
import com.example.project_login.Adapter.MenuCategoryAdapter;
import com.example.project_login.DAO.CategoryDAO;
import com.example.project_login.DAO.DrinkDAO;
import com.example.project_login.DTO.Category;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class menu_category extends AppCompatActivity {
    ProgressDialog progressDialog;
    ListView list_category;
    List<Category> listCat;
    MenuCategoryAdapter menuCategoryAdapter;
    DatabaseReference mDatabase;
    Toolbar toolbar;
    Category category;
    private static int REQUEST_CODE=1000;
    Uri imageUri;
    StorageReference storageReference;
    ImageView imageView;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_category);
        sharedPreferences=getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        list_category = findViewById(R.id.list_category);
        mDatabase = CategoryDAO.getMyDatabase();
        toolbar = findViewById(R.id.list_category_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data();
        list_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(Global.check){
                    Category category = (Category) adapterView.getAdapter().getItem(i);
                    Intent intent = new Intent(menu_category.this, drink_by_category.class);
                    intent.putExtra("Category", category.getCatName());
//                    intent.putExtra("tableID", getIntent().getStringExtra("tableID"));
//                    intent.putExtra("tableName", getIntent().getStringExtra("tableName"));
                    Bundle b = new Bundle();
                    b.putString("tableID/tableName", getIntent().getStringExtra("tableID") + "/"
                            +getIntent().getStringExtra("tableName") );
                    intent.putExtras(b);
                    startActivity(intent);
                }else {
                    Category category = (Category) adapterView.getAdapter().getItem(i);
                    Intent intent = new Intent(menu_category.this, drink_by_category.class);
                    intent.putExtra("Category", category.getCatName());
                    startActivity(intent);
                }

            }
        });
    }

    public void data(){
        listCat = new ArrayList<Category>();
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Category category = (Category) snapshot.getValue(Category.class);
                listCat.add(category);
                menuCategoryAdapter = new MenuCategoryAdapter(menu_category.this, R.layout.menu_category_item, listCat);
                list_category.setAdapter(menuCategoryAdapter);
                registerForContextMenu(list_category);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Category category = snapshot.getValue(Category.class);
                if(category == null || listCat == null || listCat.isEmpty()){
                    return;
                }
                for(int i = 0; i < listCat.size(); i++){
                    if(category.getCatName().toString().equals(listCat.get(i).getCatName().toString())){
                        listCat.remove(listCat.get(i));
                        break;
                    }
                }
                menuCategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_option_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
//                Intent intent = new Intent(menu_category.this, HomePageActivity.class);
//                startActivity(intent);
//                finish();
                onBackPressed();
                break;
            case R.id.add_item:

                if(sharedPreferences.getString("user_role","").equals("manager")){
                    Intent intent = new Intent(menu_category.this, add_drink.class);
                    startActivity(intent );
                }
                else{
                    Toast.makeText(menu_category.this, "Bạn không có quyền truy cập chức năng này !", Toast.LENGTH_SHORT).show();
                }
//                finish();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context_table, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        category = (Category) menuCategoryAdapter.getItem(pos);
        switch (item.getItemId()){
            case R.id.delete_item:
//                CategoryDAO.delete(category.getCatName(), menu_category.this);
                if(sharedPreferences.getString("user_role","").equals("manager")){
                    CategoryDAO.delete(category.getCatName(), menu_category.this);
                }
                else{
                    Toast.makeText(menu_category.this, "Bạn không có quyền truy cập chức năng này !", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.edit_item:
//               Edit(Gravity.CENTER);
                if(sharedPreferences.getString("user_role","").equals("manager")){
                    Edit(Gravity.CENTER);
                }
                else{
                    Toast.makeText(menu_category.this, "Bạn không có quyền truy cập chức năng này !", Toast.LENGTH_SHORT).show();
                }
                break;
            default:break;
        }
        return super.onContextItemSelected(item);
    }

    public void Edit(int center){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_category);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = center;
        window.setAttributes(windowAttributes);

        com.google.android.material.floatingactionbutton.FloatingActionButton category_add_img = dialog.findViewById(R.id.add_category_btn);
        Button save_btn = dialog.findViewById(R.id.save_btn);
        imageView = dialog.findViewById(R.id.category_img);

        category_add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit(dialog);
            }
        });


        dialog.show();
    }

    public void Edit(Dialog dialog){
        if(imageUri == null){
            Toast.makeText(this, "You must fill in all the information before editing", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(menu_category.this, "Success upload", Toast.LENGTH_SHORT).show();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            storageReference.child("images/"+fileName).getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            CategoryDAO.update(menu_category.this, category.getCatName(), uri.toString());
                                            dialog.dismiss();
//                                        menu_category.super.onBackPressed();
                                        }
                                    });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(menu_category.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Global.check = false;
    }
}