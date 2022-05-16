package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.project_login.Activities.Bill.ListBillActivity;
import com.example.project_login.Activities.StaffManagement.management_staff;
import com.example.project_login.Activities.StaffManagement.staff_profile;
import com.example.project_login.Activities.Table.table_management;
import com.example.project_login.DAO.UserDAO;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class HomePageActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


       // ActionBar actionBar = getSupportActionBar();
       // actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        sharedPreferences=getSharedPreferences("UserInfo",Context.MODE_PRIVATE);
        String getPhone=getIntent().getStringExtra("mobile_phone");
        DatabaseReference myDatabase= UserDAO.getMyDatabase();
        myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(getPhone)){
                    final String getRole=snapshot.child(getPhone).child("role").getValue(String.class).trim();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_phone",getPhone);
                    editor.putString("user_role",getRole);
                    editor.commit();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final com.google.android.material.card.MaterialCardView materialCardView=findViewById(R.id.profie_item);
        materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, staff_profile.class);
                startActivity(intent );
            }
        });

        final com.google.android.material.card.MaterialCardView mapMaterialCardView = findViewById(R.id.staffManagerItem);
        mapMaterialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, management_staff.class);
                startActivity(intent );
            }
        });

        final com.google.android.material.card.MaterialCardView tableMaterialCardView = findViewById(R.id.table_item);
        tableMaterialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, table_management.class);
                startActivity(intent);
            }
        });

        final com.google.android.material.card.MaterialCardView menuMaterialCardView = findViewById(R.id.menu_item);
        menuMaterialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, menu_category.class);
                startActivity(intent);
            }
        });
        final com.google.android.material.card.MaterialCardView listBillMaterialCardView = findViewById(R.id.listbill_item);
        listBillMaterialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, ListBillActivity.class);
                startActivity(intent);
            }
        });
        final com.google.android.material.card.MaterialCardView revenue = findViewById(R.id.doanhThuBTN);
        revenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, DoanhThuActivity.class);
                startActivity(intent);
            }
        });
    }

}