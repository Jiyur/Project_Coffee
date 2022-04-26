package com.example.project_login.Activities.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.project_login.DAO.UserDAO;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetNewPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseReference myDatabase= UserDAO.getMyDatabase();
       // ActionBar actionBar = getSupportActionBar();
       // actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        final com.google.android.material.textfield.TextInputEditText new_password=findViewById(R.id.new_password);
        final com.google.android.material.textfield.TextInputEditText confirm_password=findViewById(R.id.confirm_password);
        final android.widget.Button OK_BTN=findViewById(R.id.forget_ok_BTN);
        String phoneStr=getIntent().getStringExtra("mobile_phone").trim();
        OK_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new_password.getText().toString().equals(confirm_password.getText().toString())){
                    String password=new_password.getText().toString().trim();
                    myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneStr)){
                                UserDAO.newPassword(phoneStr,password);
                                Intent intent=new Intent(SetNewPassword.this,PasswordResetMessage.class);
                                startActivity(intent);
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(SetNewPassword.this, "Password doesn't match !!", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
}