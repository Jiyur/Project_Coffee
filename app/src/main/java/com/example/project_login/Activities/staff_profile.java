package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_login.Activities.LoginSignup.LoginActivity;
import com.example.project_login.DAO.UserDAO;
import com.example.project_login.DTO.User;
import com.example.project_login.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class staff_profile extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private DatabaseReference mDatabase;
    private TextView nameText, positionText, phoneText, birthText, genderText, passText;
    private Toolbar toolbar;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_profile);

        nameText = findViewById(R.id.name_textview);
        positionText = findViewById(R.id.position_textview);
        phoneText = findViewById(R.id.phone_textview);
        birthText = findViewById(R.id.birth_textview);
        genderText = findViewById(R.id.birth_textview);
        passText = findViewById(R.id.pass_textview);

        toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        mDatabase = UserDAO.getMyDatabase();
        showInfo();
    }

    public void showInfo(){
        if(sharedPreferences.contains("user_phone")){
            mDatabase.child(sharedPreferences.getString("user_phone", "")).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    user = snapshot.getValue(User.class);
                    nameText.setText(user.getFullName());
                    positionText.setText(user.getRole());
                    phoneText.setText(user.getPhone());
                    birthText.setText(user.getBirth());
                    genderText.setText(user.getGender());
                    passText.setText(user.getPassword());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_item:
                Logout();
                break;
            case R.id.changePass_item:
                changePassWord(Gravity.CENTER);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(staff_profile.this);
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("user_phone");
                editor.remove("user_role");
                editor.commit();
                Intent intent = new Intent(staff_profile.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(staff_profile.this, "Cancel logout", Toast.LENGTH_SHORT ).show();
            }
        });
        builder.create().show();
    }

    public void changePassWord(int center){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_password);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = center;
        window.setAttributes(windowAttributes);

        if(Gravity.BOTTOM == center){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }

        TextInputEditText pass_txt = dialog.findViewById(R.id.pass_txt);
        TextInputEditText newPass_txt = dialog.findViewById(R.id.newPass_txt);
        TextInputEditText repeatPass_txt = dialog.findViewById(R.id.repeatPass_txt);
        Button change_btn = dialog.findViewById(R.id.change_btn);
        Button cancel_btn = dialog.findViewById(R.id.cancell_btn);

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass_txt.getText().toString().equals(user.getPassword())){
                    if(newPass_txt.getText().toString().equals(repeatPass_txt.getText().toString())
                            && !newPass_txt.getText().toString().equals("")
                            && !repeatPass_txt.getText().toString().equals("")){
                        HashMap<String, Object> postValues = new HashMap<>();
                        postValues.put(user.getPhone()+"/password", newPass_txt.getText().toString());
                        mDatabase.updateChildren(postValues, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error == null){
                                    Toast.makeText(staff_profile.this, "Change password success", Toast.LENGTH_SHORT).show();
//                                    dialog.dismiss();
                                    return;
                                }else{
                                    Toast.makeText(staff_profile.this, "Change password fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(staff_profile.this, "Password and repeat password must be the same", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(staff_profile.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(staff_profile.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}