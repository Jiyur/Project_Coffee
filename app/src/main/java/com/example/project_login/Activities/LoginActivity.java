package com.example.project_login.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.project_login.Adapter.LoginAdapter;
import com.example.project_login.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;
//    FloatingActionButton fb,twitter,google;
    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        //Binding View
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
//        fb=findViewById(R.id.fab_facebook);
//        twitter=findViewById(R.id.fab_twitter);
//        google=findViewById(R.id.fab_google);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setAdapter(new LoginAdapter(this));

        new TabLayoutMediator(tabLayout,viewPager,(tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Sign in");
                    break;
                case 1:
                    tab.setText("Sign up");
                    break;
            }
        }).attach();


        tabLayout.setTranslationY(300);


        tabLayout.setTranslationY(v);


        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();

    }
}