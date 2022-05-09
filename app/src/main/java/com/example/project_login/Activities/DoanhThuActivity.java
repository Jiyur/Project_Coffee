package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_login.DAO.BillDAO;
import com.example.project_login.DTO.Bill;
import com.example.project_login.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DoanhThuActivity extends AppCompatActivity {
    TextView textViewDoanhThuTong,textViewDoanhThuThang;
    public static Bill billData = new Bill();
    private CombinedChart mChart;
    ArrayList<Bill> arrayListBill = new ArrayList<Bill>();
    ArrayList<String> arrayListItem = new ArrayList<String>();
    int[] doanhthuThang = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu);
        init();
        LoadData();
        //taoBang();

    }
    private void init(){
        textViewDoanhThuThang=findViewById(R.id.textViewDoanhThuThang);
        textViewDoanhThuTong=findViewById(R.id.textViewDoanhThuTong);
    }
    private void LoadData(){
        DatabaseReference myDatabase = BillDAO.getMyDatabase();
        myDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListBill.clear();
                arrayListItem.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    billData = postSnapshot.getValue(Bill.class);
                    arrayListBill.add(billData);
                }
                tinhDoanhThuTong();
                tinhDoanhThuCacThang();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void tinhDoanhThuTong(){
        int totalMoney=0;

        for (Bill bill:arrayListBill) {
            totalMoney+= bill.getTotal();
        }

        textViewDoanhThuTong.setText("Doanh Thu Tổng: "+totalMoney+" VND");
    }

    private void tinhDoanhThuCacThang(){
        Date date;
        for (Bill bill:arrayListBill) {
            try {
                date=new SimpleDateFormat("dd/MM/yyyy").parse(bill.getTime());
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
                int month= Integer.parseInt(dateFormat.format(date));
                //Log.d("OK", "tinhDoanhThuCacThang: "+month);
                switch (month){
                    case 1:
                        //Log.d("OK", "tinhDoanhThuCacThang: "+month);
                        doanhthuThang[0]+=bill.getTotal();
                        break;
                    case 2:
                        doanhthuThang[1]+=bill.getTotal();
                        break;
                    case 3:
                        doanhthuThang[2]+=bill.getTotal();
                        break;
                    case 4:
                        doanhthuThang[3]+=bill.getTotal();
                        break;
                    case 5:
                        doanhthuThang[4]+=bill.getTotal();
                        break;
                    case 6:
                        doanhthuThang[5]+=bill.getTotal();
                        break;
                    case 7:
                        doanhthuThang[6]+=bill.getTotal();
                        break;
                    case 8:
                        doanhthuThang[7]+=bill.getTotal();
                        break;
                    case 9:
                        doanhthuThang[8]+=bill.getTotal();
                        break;
                    case 10:
                        doanhthuThang[9]+=bill.getTotal();
                        break;
                    case 11:
                        doanhthuThang[10]+=bill.getTotal();
                        break;
                    case 12:
                        doanhthuThang[11]+=bill.getTotal();
                        break;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        for (int i=0;i<12;i++) {
            Log.d("OK", "tinhDoanhThuCacThang "+i +": "+doanhthuThang[i]);
        }
    }

    /*private void taoBang(){
        mChart = (CombinedChart) findViewById(R.id.combinedChart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) this);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        final List<String> xLabel = new ArrayList<>();
        xLabel.add("Jan");
        xLabel.add("Feb");
        xLabel.add("Mar");
        xLabel.add("Apr");
        xLabel.add("May");
        xLabel.add("Jun");
        xLabel.add("Jul");
        xLabel.add("Aug");
        xLabel.add("Sep");
        xLabel.add("Oct");
        xLabel.add("Nov");
        xLabel.add("Dec");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart());

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
    }*/

//    @Override
//    public void onValueSelected(Entry e, Highlight h) {
//        Toast.makeText(this, "Value: "
//                + e.getY()
//                + ", index: "
//                + h.getX()
//                + ", DataSet index: "
//                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onNothingSelected() {
//
//    }

    /*private static DataSet dataChart() {

        LineData d = new LineData();
        int[] data = new int[] { 1, 2, 2, 1, 1, 1, 2, 1, 1, 2, 1, 9 };

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 12; index++) {
            entries.add(new Entry(index, data[index]));
        }

        LineDataSet set = new LineDataSet(entries, "Request Ots approved");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }*/
}