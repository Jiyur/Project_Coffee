package com.example.project_login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {
    EditText password,phone,confirm;
    android.widget.Button signup;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle saveInstanceState)
    {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);
        password=root.findViewById(R.id.pass);
        phone=root.findViewById(R.id.phone);
        confirm=root.findViewById(R.id.confirm_password);
        signup=root.findViewById(R.id.signUp);

        password.setTranslationX(800);
        confirm.setTranslationX(800);
        phone.setTranslationX(800);
        signup.setTranslationX(800);

        password.setAlpha(0);
        confirm.setAlpha(0);
        phone.setAlpha(0);
        signup.setAlpha(0);

        phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirm.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();



        return root;
    }
}
