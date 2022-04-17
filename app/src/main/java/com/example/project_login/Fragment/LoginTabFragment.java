package com.example.project_login.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_login.R;

public class LoginTabFragment  extends Fragment {
    EditText phone,pass;
    TextView forgetPass;
    Button login_BTN;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);

        phone=root.findViewById(R.id.phone);
        pass=root.findViewById(R.id.pass);
        forgetPass=(TextView) root.findViewById(R.id.forget_pass);
        login_BTN=root.findViewById(R.id.login_BTN);

        phone.setTranslationX(800);
        pass.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login_BTN.setTranslationX(800);

        phone.setAlpha(0);
        pass.setAlpha(0);
        forgetPass.setAlpha(0);
        login_BTN.setAlpha(0);

        phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login_BTN.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }
}
