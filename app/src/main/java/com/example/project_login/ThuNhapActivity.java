package com.example.project_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project_login.DAO.BillDAO;
import com.example.project_login.DTO.Bill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ThuNhapActivity extends AppCompatActivity {

    Button btnTuNgay,btnDenNgay,btnDoanhThu;
    EditText edTuNgay,edDenNgay;
    TextView tvDoanhThu;
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    int mYear,mDay, mMonth;

    ArrayList<Bill> arrayListBill = new ArrayList<Bill>();
    public static Bill billData = new Bill();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_nhap);
        init();
        actionButton();
        LoadData();

    }
    void init(){
        btnTuNgay=findViewById(R.id.btnTuNgay);
        btnDenNgay=findViewById(R.id.btnDenNgay);
        btnDoanhThu=findViewById(R.id.btnDoanhThuNgay);
        edTuNgay=findViewById(R.id.edTuNgay);
        edDenNgay=findViewById(R.id.edDenNgay);
        tvDoanhThu=findViewById(R.id.tvDoanhThuNgay);
    }
    DatePickerDialog.OnDateSetListener mDateTuNgay= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            mYear=year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;
            GregorianCalendar c= new GregorianCalendar(mYear,mMonth,mDay);
            edTuNgay.setText(sdf.format(c.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener mDateDenNgay= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            mYear=year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;
            GregorianCalendar c= new GregorianCalendar(mYear,mMonth,mDay);
            edDenNgay.setText(sdf.format(c.getTime()));
        }
    };

    void actionButton(){
        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c=Calendar.getInstance();
                mYear=c.get(Calendar.YEAR);
                mMonth=c.get(Calendar.MONTH);
                mDay=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d= new DatePickerDialog(ThuNhapActivity.this,0,mDateTuNgay,mYear,mMonth,mDay);
                d.show();
            }
        });
        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c=Calendar.getInstance();
                mYear=c.get(Calendar.YEAR);
                mMonth=c.get(Calendar.MONTH);
                mDay=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d= new DatePickerDialog(ThuNhapActivity.this,0,mDateDenNgay,mYear,mMonth,mDay);
                d.show();
            }
        });
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tuNgay= edTuNgay.getText().toString();
                String denNgay= edDenNgay.getText().toString();
                tinhTong(tuNgay,denNgay);
            }
        });
    }
    private void LoadData(){
        DatabaseReference myDatabase = BillDAO.getMyDatabase();
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListBill.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    billData = postSnapshot.getValue(Bill.class);
                    arrayListBill.add(billData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void tinhTong(String dateTruoc, String dateSau){
        Date dateBill;
        int tien=0;
        int flag=1;
        for (Bill bill: arrayListBill) {

            try {
                dateBill = new SimpleDateFormat("dd-MM-yyyy").parse(bill.getTime());
                /*if((dateBill.after(sdf.parse(dateTruoc)) && dateBill.before(sdf.parse(dateSau))
                        || dateBill.equals(dateTruoc))){

                }*/
                if(dateBill.compareTo(sdf.parse(dateTruoc))>=0 && dateBill.compareTo(sdf.parse(dateSau))<=0){
                    tien+=bill.getTotal();
                    Log.d("OK", "tinhTong: "+tien);
                }
                else{
                    flag=0;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(flag==1){
            tvDoanhThu.setText("Doanh thu là: "+tien);
        }else{
            tvDoanhThu.setText("Doanh thu: Vui lòng nhập lại ngày hợp lệ");
        }
    }
}