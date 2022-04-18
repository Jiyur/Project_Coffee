package com.example.project_login.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_login.DTO.User;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupTabFragment extends Fragment {
    EditText password,user_name,phone;
    android.widget.Button signup;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle saveInstanceState)
    {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);
        password=root.findViewById(R.id.password);
        user_name=root.findViewById(R.id.name);
        phone=root.findViewById(R.id.phone_reg);
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
        DatabaseReference myDatabase= FirebaseDatabase.getInstance("https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneStr=phone.getText().toString().trim();
                String passwordStr=password.getText().toString().trim();
                String fullName=user_name.getText().toString().trim();
                if(!phoneStr.isEmpty()
                ||!passwordStr.isEmpty()
                ||!fullName.isEmpty()){
                    myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneStr)){
                                Toast.makeText(getContext(),"This phone already exists",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                User user=new User(fullName,phoneStr,passwordStr,"staff");
                                myDatabase.child(phoneStr).setValue(user);
                                Toast.makeText(getContext(),"Register Successful",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }







            }
        });


        return root;
    }
}
