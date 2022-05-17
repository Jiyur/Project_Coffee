package com.example.project_login.Activities.Order;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_login.DAO.BillDAO;
import com.example.project_login.DAO.DrinksDAO;
import com.example.project_login.DAO.TableDAO;
import com.example.project_login.DTO.Bill;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.DTO.Table;
import com.example.project_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderActivity extends AppCompatActivity {
    ImageView imgDrinks;
    TextView txtName, txtQuantity, btnadd, btnMinus;
    Toolbar toolbar;
    Drinks drinks = new Drinks();
    Table table = new Table();
    Bill bill = new Bill();

    String drinksID = "", billID = "", tableID = "", tableName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
//        imgDrinks = findViewById(R.id.imageviewdrink);
//        btnadd = findViewById(R.id.buttonadd);
//        btnMinus = findViewById(R.id.buttonminus);
//        txtName = findViewById(R.id.textviewname);
//        txtQuantity = findViewById(R.id.textviewquantity);
        toolbar = findViewById(R.id.orderDrink_toolbar);

//        drinksID = getIntent().getStringExtra("drinkID");
//        tableID = getIntent().getStringExtra("tableID");
//        tableName = getIntent().getStringExtra("tableName");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Init();
        LoadData();
        AddEvents();
    }

    private void AddEvents() {
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer temp = Integer.parseInt( (String) txtQuantity.getText() );
                temp += 1;
                txtQuantity.setText( temp.toString() );
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer temp = Integer.parseInt( (String) txtQuantity.getText() );
                temp = (temp > 0) ? temp-1 : 0;
                txtQuantity.setText( temp.toString() );
            }
        });
    }

    private void LoadData() {
        DatabaseReference myDatabase = DrinksDAO.getMyDatabase();
        myDatabase.child(drinksID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                drinks = snapshot.getValue(Drinks.class);
                txtName.setText(drinks.getName());
                Picasso.with(OrderActivity.this).load(drinks.getImage())
                        .fit()
                        .into(imgDrinks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myDatabase = TableDAO.getMyDatabase();
        myDatabase.child(tableID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                table = snapshot.getValue(Table.class);
                if(table.getStatus().equals("yes")){
                    billID = table.getIdBill();
                    getBill();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public void getBill(){
        DatabaseReference myDatabase = BillDAO.getMyDatabase();
        myDatabase.child(billID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bill = snapshot.getValue(Bill.class);

                for(int i=0; i<bill.listDrinks.size(); ++i){
                    if(bill.listDrinks.get(i).getId().equals(drinksID)){
                        txtQuantity.setText(bill.listQuantity.get(i).toString());
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Init() {
        imgDrinks = findViewById(R.id.imageviewdrink);
        btnadd = findViewById(R.id.buttonadd);
        btnMinus = findViewById(R.id.buttonminus);
        txtName = findViewById(R.id.textviewname);
        txtQuantity = findViewById(R.id.textviewquantity);

        String temp = null;
        Bundle b = getIntent().getExtras();
        if(b != null)
            temp = b.getString("tableID/tableName/drinksID");
        int change = 0;
        for(int i=0; i<temp.length(); ++i){
            if(temp.charAt(i) == '/'){
                change += 1;
                continue;
            }
            if(change == 0) {
                tableID = tableID + temp.charAt(i);
            } else if(change == 1){
                tableName = tableName + temp.charAt(i);
            } else {
                drinksID = drinksID + temp.charAt(i);
            }
        }
    }

    public void btnOrder_Click(View view) {
        Integer quantity = Integer.parseInt( (String) txtQuantity.getText() );

        SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date currentTime = Calendar.getInstance().getTime();
        String time = formatter.format(currentTime);

        if(table.getStatus().equals("no")){
            table.setStatus("yes");
            table.setIdBill("Table: " + tableName + " - " + time);
            TableDAO.update(table, OrderActivity.this);

            bill = new Bill(table.getIdBill(), tableName, time);
        }

        bill.UpdateQuantity(drinks, quantity);

        if(bill.listQuantity.size() > 0) {
            BillDAO.addOrUpdate(bill.getId(), bill);
        } else {
            //cap nhat ban khong co nguoi ngoi
            table.setStatus("no");
            TableDAO.update(table, OrderActivity.this);

            //xoa bill
            BillDAO.delete(bill.getId(), OrderActivity.this);
        }

        finish();
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