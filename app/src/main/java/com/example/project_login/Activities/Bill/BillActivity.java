package com.example.project_login.Activities.Bill;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_login.Adapter.ItemBillAdapter;
import com.example.project_login.DAO.BillDAO;
import com.example.project_login.DAO.TableDAO;
import com.example.project_login.DTO.Bill;
import com.example.project_login.DTO.Table;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class BillActivity extends AppCompatActivity {
    String billID = "", tableID = "";
    Button btnBack;
    Button btnPayment;
    ListView lvBill;
    TextView txtNotification;
    TextView txtTotal;
    TextView txtTitle;
    TextView txtTime;
    ItemBillAdapter itemBillAdapter;

    public Bill billData = null;
    public Table tableData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Init();
        LoadData();
    }

    private void Init() {
        lvBill = (ListView) findViewById(R.id.listviewBill);
        txtNotification = (TextView) findViewById(R.id.textviewnotification);
        txtTotal = (TextView) findViewById(R.id.textviewtotal);
        btnPayment = (Button) findViewById(R.id.buttonpayment);
        btnBack = (Button) findViewById(R.id.buttonback);
        txtTitle = (TextView) findViewById(R.id.textviewtittle);
        txtTime = (TextView) findViewById(R.id.textviewtime);

        Bundle b = getIntent().getExtras();
        String temp = null;
        if(b != null)
            temp = b.getString("tableID/billID");
        boolean change = false;
        for(int i=0; i<temp.length(); ++i){
            if(temp.charAt(i) == '/'){
                change = true;
                continue;
            }
            if(change){
                billID = billID + temp.charAt(i);
            }
            else{
                tableID = tableID + temp.charAt(i);
            }
        }

    }

    private void LoadData() {
        DatabaseReference myDatabaseBill = BillDAO.getMyDatabase();
        myDatabaseBill.child(billID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                billData = snapshot.getValue(Bill.class);

                if(billData.listDrinks.size()<=0 || billData.listQuantity.size()<=0){
                    itemBillAdapter.notifyDataSetChanged();
                    txtNotification.setVisibility(View.VISIBLE);
                    lvBill.setVisibility(View.INVISIBLE);
                } else {
                    itemBillAdapter = new ItemBillAdapter(BillActivity.this, billData.listDrinks, billData.listQuantity);
                    txtNotification.setVisibility(View.INVISIBLE);
                    lvBill.setVisibility(View.VISIBLE);
                }

                lvBill.setAdapter(itemBillAdapter);

                txtTitle.setText("Hóa đơn bàn: " + billData.getTable());
                txtTime.setText(billData.getTime());

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtTotal.setText(decimalFormat.format(billData.getTotal()) + "Đ");

                if(billData.isPayment()){
                    btnPayment.setText("Đã thanh toán");
                } else {
                    btnPayment.setText("Thanh toán");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BillActivity.this, "Check your connect!", Toast.LENGTH_LONG).show();
            }
        });


        if(tableID.equals("")) return;
        DatabaseReference myDatabase = TableDAO.getMyDatabase();
        myDatabase.child(tableID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tableData = (Table) snapshot.getValue(Table.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void btnPayment_Click(View view) {
        if(billData.isPayment()){
            Toast.makeText(BillActivity.this, "Đã thanh toán!", Toast.LENGTH_LONG).show();
            return;
        }
        if(tableID.equals("")){
            Toast.makeText(BillActivity.this, "Không thể thanh toán!", Toast.LENGTH_LONG).show();
            return;
        }

        billData.setPayment(true);
        BillDAO.addOrUpdate(billID, billData);
        //Toast.makeText(BillActivity.this, "Thanh toán thành công!", Toast.LENGTH_LONG).show();

        tableData.setStatus("no");
        TableDAO.update(tableData,BillActivity.this);
        btnPayment.setText("Đã thanh toán");
    }

    public void btnbBack_Click(View view) {
        finish();
    }
}