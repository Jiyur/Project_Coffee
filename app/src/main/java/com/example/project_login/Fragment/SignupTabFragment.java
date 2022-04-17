package com.example.project_login.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_login.Activities.SendOTPActivity;
import com.example.project_login.R;

public class SignupTabFragment extends Fragment {
    EditText password,user_name,phone;
    android.widget.Button signup;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle saveInstanceState)
    {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);
        password=root.findViewById(R.id.password);
        user_name=root.findViewById(R.id.name);
        phone=root.findViewById(R.id.phone);
        signup=root.findViewById(R.id.signUp);

        password.setTranslationX(800);
        phone.setTranslationX(800);
        user_name.setTranslationX(800);
        signup.setTranslationX(800);

        password.setAlpha(0);
        phone.setAlpha(0);
        user_name.setAlpha(0);
        signup.setAlpha(0);

        user_name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!phone.getText().toString().trim().isEmpty()
               ||!password.getText().toString().trim().isEmpty()
               ||!user_name.getText().toString().trim().isEmpty()){

               }



            }
        });


        return root;
    }
}
