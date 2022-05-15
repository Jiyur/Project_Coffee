package com.example.project_login.Activities.Table;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.project_login.Activities.Bill.BillActivity;
import com.example.project_login.Activities.HomePageActivity;
import com.example.project_login.Activities.Order.MenuOrderActivity;
import com.example.project_login.Adapter.TableAdapter;
import com.example.project_login.DAO.BillDAO;
import com.example.project_login.DAO.TableDAO;
import com.example.project_login.DTO.Bill;
import com.example.project_login.DTO.Table;
import com.example.project_login.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class table_management extends AppCompatActivity {
    GridView gridView;
    TableAdapter tableAdapter;
    Toolbar toolbar;
    List<Table> tableList;
    private DatabaseReference mDatabase;
    private Table table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_management);

        gridView = findViewById(R.id.table_gridView);
        toolbar = findViewById(R.id.listTable_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = TableDAO.getMyDatabase();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                optionOfTable(Gravity.CENTER, i);
            }
        });
        showTable();
    }

    public void showTable(){
        tableList = new ArrayList<Table>();
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                HashMap<String, Object> postValues = new HashMap<>();
                postValues.put(snapshot.getKey()+"/idTable", snapshot.getKey());
                mDatabase.updateChildren(postValues);
                Table table = snapshot.getValue(Table.class);
                table.setIdTable(snapshot.getKey());
                tableList.add(table);
                tableAdapter = new TableAdapter(table_management.this, R.layout.list_table_item, tableList);
                gridView.setAdapter(tableAdapter);
                registerForContextMenu(gridView);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Table table = snapshot.getValue(Table.class);
                for(int i=0; i<tableList.size(); ++i){
                    if(tableList.get(i).getIdTable().equals(table.getIdTable())){
                        tableList.set(i, table);
                    }
                }

                tableAdapter = new TableAdapter(table_management.this, R.layout.list_table_item, tableList);
                gridView.setAdapter(tableAdapter);
                registerForContextMenu(gridView);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
               Table table = snapshot.getValue(Table.class);
                if(table == null || tableList == null || tableList.isEmpty()){
                    return;
                }
                for(int i = 0; i < tableList.size(); i++){
                    if(table.getIdTable().toString().equals(tableList.get(i).getIdTable().toString())){
                        Toast.makeText(table_management.this, table.getIdTable(), Toast.LENGTH_SHORT ).show();
                        tableList.remove(tableList.get(i));
                        break;
                    }
                }
                tableAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_option_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.add_item:
                add_table();
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void add_table(){
        AlertDialog.Builder builder = new AlertDialog.Builder(table_management.this);
        builder.setMessage("Would you like to add a table?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TableDAO.insert(table_management.this);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(table_management.this, "Cancel delete", Toast.LENGTH_SHORT ).show();
            }
        });
        builder.create().show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context_table, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        table = (Table) tableAdapter.getItem(pos);
        switch (item.getItemId()){
            case R.id.delete_item:
                TableDAO.delete(table.getIdTable(), table_management.this);
                break;
            case R.id.edit_item:
                editTable(Gravity.CENTER);
                break;
            default:break;
        }
        return super.onContextItemSelected(item);
    }

    public void editTable(int center){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_table);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = center;
        window.setAttributes(windowAttributes);

        TextInputEditText idBill_txt = dialog.findViewById(R.id.idBill_txt);
        TextInputEditText status_txt = dialog.findViewById(R.id.status_txt);
        Button save_btn = dialog.findViewById(R.id.save_btn);
        Button cancel_btn = dialog.findViewById(R.id.cancell_btn);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableDAO.update(new Table(table.getIdTable(), idBill_txt.getText().toString(),
                        status_txt.getText().toString()), table_management.this);
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(table_management.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public void optionOfTable(int center, int pos){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_order_pay);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = center;
        window.setAttributes(windowAttributes);
        ImageButton order_imgBtn = dialog.findViewById(R.id.order_imgBtn);
        ImageButton  pay_imgBtn = dialog.findViewById(R.id.pay_imgBtn);

        order_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(table_management.this, MenuOrderActivity.class);
                Bundle b = new Bundle();
                b.putString("tableID/tableName", tableList.get(pos).getIdTable() + "/" + ((Integer) (pos + 1)).toString() );
                intent.putExtras(b);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        pay_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tableList.get(pos).getStatus().equals("no")){
                    Toast.makeText(table_management.this, "Empty table", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(table_management.this, BillActivity.class);
                    Bundle b = new Bundle();
                    b.putString("tableID/billID", tableList.get(pos).getIdTable() + "/" + tableList.get(pos).getIdBill());
                    intent.putExtras(b);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}