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

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseReference myDatabase= UserDAO.getMyDatabase();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        final android.widget.Button nextBTN=findViewById(R.id.forget_nextBTN);
        final com.google.android.material.textfield.TextInputEditText phoneForget=findViewById(R.id.phone_forget_txt);
        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneStr=phoneForget.getText().toString().trim();
                myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(phoneStr)){
                            Intent intent=new Intent(ForgetPasswordActivity.this,SendOTPActivity.class);
                            intent.putExtra("action","reset");
                            intent.putExtra("mobile_phone",phoneStr.substring(1));
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(ForgetPasswordActivity.this, "This phone number doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}