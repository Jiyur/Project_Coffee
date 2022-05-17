package com.example.project_login.Activities.Bill;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_login.Activities.Global;
import com.example.project_login.Activities.Table.table_management;
import com.example.project_login.Adapter.ItemInfoAdapter;
import com.example.project_login.DAO.BillDAO;
import com.example.project_login.DTO.Bill;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class ListBillActivity extends AppCompatActivity {
    TextView txtTittle, sum_txt;
    ListView lvBill;
    Toolbar toolbar;
    ItemInfoAdapter myAdapter;
    ArrayList<Bill> arrayListBill;
    final Calendar c = Calendar.getInstance();
    int from_day = c.get(Calendar.DAY_OF_MONTH);
    int from_month = c.get(Calendar.MONTH);
    int from_year = c.get(Calendar.YEAR);

    int to_day = c.get(Calendar.DAY_OF_MONTH);
    int to_month = c.get(Calendar.MONTH);
    int to_year = c.get(Calendar.YEAR);
    int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bill);
        txtTittle = (TextView) findViewById(R.id.textviewtittle);
        sum_txt = findViewById(R.id.sum_txt);
        lvBill = (ListView) findViewById(R.id.listviewBillInfo);
        arrayListBill=new ArrayList<Bill>();
        myAdapter=new ItemInfoAdapter(this,R.layout.bill_info_layout,arrayListBill);
        lvBill.setAdapter(myAdapter);
        toolbar = findViewById(R.id.listBill_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bill bill=(Bill) lvBill.getItemAtPosition(position);
                Intent intent = new Intent(ListBillActivity.this, BillActivity.class);
                Bundle b = new Bundle();
                b.putString("tableID/billID", "/"  + bill.getId());
                intent.putExtra("bill", "bill");
                intent.putExtras(b);
                startActivity(intent);
                Global.checkBill = true;
            }
        });

        getData();
    }

    public void getData(){
        DatabaseReference myDatabase = FirebaseDatabase
                .getInstance("https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Bill");
        myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListBill.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Bill billData = postSnapshot.getValue(Bill.class);
                    sum += Integer.parseInt(billData.getTotal().toString());
                    arrayListBill.add(billData);
                    Log.e("bill",billData.getId());
                }
                sum_txt.setText(String.valueOf("Tổng doanh thu: " + sum + " VND"));
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option_list_bill, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.chooseDate_item:
                chooseDate(Gravity.CENTER);
                return true;
            case R.id.chooseAll_item:
                getData();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void chooseDate(int center){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_choose_date);

        DatePicker datePicker_from = dialog.findViewById(R.id.datePicker_from);
        DatePicker datePicker_to = dialog.findViewById(R.id.datePicker_to);
        Button xem_btn = dialog.findViewById(R.id.xem_btn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker_to.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    to_day = i2;
                    to_month = i1;
                    to_year = i;
//                    Toast.makeText(ListBillActivity.this,  to_day +"/"+ to_month +"/"+ to_year, Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker_from.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    from_day = i2;
                    from_month = i1;
                    from_year = i;
//                    Toast.makeText(ListBillActivity.this,  from_day +"/"+ from_month +"/"+ from_year, Toast.LENGTH_SHORT).show();
                }
            });
        }

//        Toast.makeText(this,  from_day +"/"+ from_month +"/"+ from_year, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,  to_day +"/"+ to_month +"/"+ to_year, Toast.LENGTH_SHORT).show();
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = center;
        window.setAttributes(windowAttributes);

        xem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((to_year - from_year) < 0 || (to_month - from_month) < 0 || (to_day - from_day) < 0){
                    Toast.makeText(ListBillActivity.this, "khoảng ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                }else{
                    getInAbout(from_day, from_month, from_year, to_day, to_month, to_year);
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }



    public void getInAbout(int from_day, int from_month, int from_year,
                           int to_day, int to_month, int to_year){
        DatabaseReference myDatabase = FirebaseDatabase
                .getInstance("https://coffee-42174-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Bill");
        myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListBill.clear();
                sum = 0;
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Bill bill = postSnapshot.getValue(Bill.class);
                    getBill(from_day, from_month, from_year, to_day, to_month, to_year, bill);
                }
                sum_txt.setText(String.valueOf("Tổng doanh thu: " +sum + " VND"));
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getBill(int from_day, int from_month, int from_year,
                        int to_day, int to_month, int to_year, Bill bill){

        String bill_id = bill.getId().toString();
        String[] bill_id_split = bill_id.split("\\s");
        String bill_date = bill_id_split[3];
        String[] bill_date_split = bill_date.split("-");
        int bill_year = Integer.parseInt(bill_date_split[2]);
        int bill_month = Integer.parseInt(bill_date_split[1]);
        int bill_day = Integer.parseInt(bill_date_split[0]);
        if(from_year <= bill_year && bill_year <= to_year){
            if(from_month + 1  <= bill_month && bill_month  <= to_month + 1){
                if(from_day <= bill_day && bill_day <= to_day){
                    arrayListBill.add(bill);
                    sum += Integer.parseInt(bill.getTotal().toString());
                }
            }
        }
    }
}