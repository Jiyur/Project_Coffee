package com.example.project_login.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.project_login.Fragment.LoginTabFragment;
import com.example.project_login.Fragment.SignupTabFragment;

public class LoginAdapter extends FragmentStateAdapter {

    public LoginAdapter(FragmentActivity fa){
        super(fa);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new LoginTabFragment();
            case 1:
                return new SignupTabFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
