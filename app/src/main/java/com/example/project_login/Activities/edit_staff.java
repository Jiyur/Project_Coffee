package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_login.DTO.User;
import com.example.project_login.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class edit_staff extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private User user;
    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;

    TextView back_txt, birth_txt;
    EditText fullname_txt, phone_txt, pass_txt ;
    RadioButton male_radioBtn, female_radioBtn, staff_radioBtn, manager_radioBtn;
    Button save_btn, chooseDate_btn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff);

        final Calendar c = Calendar.getInstance();
        this.lastSelectedYear = c.get(Calendar.YEAR);
        this.lastSelectedMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        save_btn = findViewById(R.id.save_btn);
        chooseDate_btn = findViewById(R.id.chooseDate_btn);
        male_radioBtn = findViewById(R.id.male_radioBtn);
        female_radioBtn = findViewById(R.id.female_radioBtn);
        staff_radioBtn = findViewById(R.id.staff_radioBtn);
        manager_radioBtn = findViewById(R.id.manager_radioBtn);
        fullname_txt = findViewById(R.id.fullname_txt);
        phone_txt = findViewById(R.id.phone_txt);
        pass_txt = findViewById(R.id.pass_txt);
        birth_txt = findViewById(R.id.birth_txt);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getParcelable("User");

        toolbar = findViewById(R.id.listStaff_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData(user);
    }

    public void chooseOnclick(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                birth_txt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                lastSelectedYear = year;
                lastSelectedMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };
        DatePickerDialog datePickerDialog = null;
        datePickerDialog = new DatePickerDialog(this,
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        datePickerDialog.show();
    }

    public void saveOnclick(View view) {
        user.setFullName(fullname_txt.getText().toString());
        user.setPhone(phone_txt.getText().toString());
        user.setPassword(pass_txt.getText().toString());
        user.setBirth(birth_txt.getText().toString());
        String role = "";
        String gender = "";
        if(staff_radioBtn.isChecked()){ role = "staff"; }
        else{
            if(manager_radioBtn.isChecked()){role = "manager"; }
        }
        if(male_radioBtn.isChecked()){ gender= "male"; }
        else{
            if(female_radioBtn.isChecked()){ gender = "female"; }
        }
        user.setRole(role);
        user.setGender(gender);
        upDateUser(user);
    }

    public void getData(User user){
        fullname_txt.setText(user.getFullName());
        phone_txt.setText(user.getPhone());
        pass_txt.setText(user.getPassword());
        if(user.getGender().equals("male")){
            male_radioBtn.setChecked(true);
        }else {
            female_radioBtn.setChecked(true);
        }
        if(user.getRole().equals("manager")){
            manager_radioBtn.setChecked(true);
        } else{
            staff_radioBtn.setChecked(true);
        }
        birth_txt.setText(user.getBirth().replace('/', '-'));
    }

    private void upDateUser(User user) {
        HashMap<String, Object> postValues = new HashMap<>();
        postValues.put(user.getPhone()+"/fullName", user.getFullName());
        postValues.put(user.getPhone()+"/phone", user.getPhone());
        postValues.put(user.getPhone()+"/password", user.getPassword());
        postValues.put(user.getPhone()+"/role", user.getRole());
        postValues.put(user.getPhone()+"/gender", user.getGender());
        postValues.put(user.getPhone()+"/birth", user.getBirth());
        mDatabase.updateChildren(postValues, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error == null){
                    Toast.makeText(edit_staff.this, "Edit success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(edit_staff.this, "Edit fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}