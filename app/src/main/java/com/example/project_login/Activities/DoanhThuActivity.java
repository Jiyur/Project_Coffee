package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.project_login.DAO.BillDAO;
import com.example.project_login.DTO.Bill;
import com.example.project_login.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DoanhThuActivity extends AppCompatActivity {
    BarChart barChart;
    TextView total_tv;
    ArrayList<Bill> arrayListBill = new ArrayList<Bill>();
    int[] RevenueMonthly = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_doanh_thu);
        setTitle("Bar Chart");
        LoadData();

        barChart=findViewById(R.id.doanhthuThang_chart);
        total_tv=findViewById(R.id.total_TextView);


    }
    public void LoadData(){
        DatabaseReference myDatabase = BillDAO.getMyDatabase();

        myDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListBill.clear();
                RevenueMonthly = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Bill billData = postSnapshot.getValue(Bill.class);
                    arrayListBill.add(billData);
                }
                MonthlyCalc();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void MonthlyCalc(){
        Date date;
        for (Bill bill:arrayListBill) {
            try {
                date=new SimpleDateFormat("dd/MM/yyyy").parse(bill.getTime());
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
                int month= Integer.parseInt(dateFormat.format(date));
                Log.e("Month: ",String.valueOf(month));
                switch (month){
                    case 1:
                        RevenueMonthly[0]+=bill.getTotal();
                        break;
                    case 2:
                        RevenueMonthly[1]+=bill.getTotal();
                        break;
                    case 3:
                        RevenueMonthly[2]+=bill.getTotal();
                        break;
                    case 4:
                        RevenueMonthly[3]+=bill.getTotal();
                        break;
                    case 5:
                        RevenueMonthly[4]+=bill.getTotal();
                        break;
                    case 6:
                        RevenueMonthly[5]+=bill.getTotal();
                        break;
                    case 7:
                        RevenueMonthly[6]+=bill.getTotal();
                        break;
                    case 8:
                        RevenueMonthly[7]+=bill.getTotal();
                        break;
                    case 9:
                        RevenueMonthly[8]+=bill.getTotal();
                        break;
                    case 10:
                        RevenueMonthly[9]+=bill.getTotal();
                        break;
                    case 11:
                        RevenueMonthly[10]+=bill.getTotal();
                        break;
                    case 12:
                        RevenueMonthly[11]+=bill.getTotal();
                        break;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        ArrayList<BarEntry> revenue=new ArrayList<>();
        int total=0;
        for(int i=0;i<12;i++){
            revenue.add(new BarEntry(i+1,RevenueMonthly[i]));
            total+=RevenueMonthly[i];
        }

        BarDataSet barDataSet=new BarDataSet(revenue,"Doanh Thu");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(14f);
        BarData barData=new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Doanh thu tháng");
        barChart.animateY(1000 );
        String text="Tổng thu: "+String.valueOf(total);
        total_tv.setText(text);



    }
}