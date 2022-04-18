package com.example.project_login.Activities.LoginSignup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project_login.R;

public class PasswordResetMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword_message);
        final android.widget.Button login_BTN=findViewById(R.id.reset_login_BTN);
        login_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PasswordResetMessage.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}