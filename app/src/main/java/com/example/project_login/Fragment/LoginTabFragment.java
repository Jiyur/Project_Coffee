package com.example.project_login.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_login.Activities.HomePageActivity;
import com.example.project_login.Activities.LoginSignup.ForgetPasswordActivity;
import com.example.project_login.Activities.LoginSignup.SendOTPActivity;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginTabFragment  extends Fragment {
    EditText phone,pass;
    TextView forgetPass;
    Button login_BTN;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        DatabaseReference myDatabase= FirebaseDatabase.getInstance("https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
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

        login_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneStr=phone.getText().toString().trim();
                String passStr=pass.getText().toString().trim();
                if(!phoneStr.isEmpty()
                ||!passStr.isEmpty()){
                    myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneStr)){
                                final String getPassword=snapshot.child(phoneStr).child("password").getValue(String.class).trim();

                                if(getPassword.equals(passStr)){
                                    if(snapshot.child(phoneStr).child("isVerified").getValue(String.class).trim().equals("No")){
                                        Intent intent=new Intent(getActivity(), SendOTPActivity.class);
                                        intent.putExtra("mobile_phone",phoneStr.substring(1));
                                        intent.putExtra("action","login");
                                        startActivity(intent);
                                    }
                                    else{
                                        Intent intent=new Intent(getActivity(), HomePageActivity.class);
                                        startActivity(intent);
                                    }
//
                                }

                                else{
                                    Toast.makeText(getContext(), "Wrong phone number or password", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
