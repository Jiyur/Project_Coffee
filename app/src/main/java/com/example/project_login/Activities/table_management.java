package com.example.project_login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.project_login.Adapter.TableAdapter;
import com.example.project_login.DAO.TableDAO;
import com.example.project_login.DAO.UserDAO;
import com.example.project_login.DTO.Table;
import com.example.project_login.DTO.User;
import com.example.project_login.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
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
                Intent intent = new Intent(table_management.this, HomePageActivity.class);
                startActivity(intent);
                finish();
//                onBackPressed();
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
//                Intent intent = new Intent(table_management.this, edit_staff.class);
//                intent.putExtra("table", table);
//                startActivity(intent);
                break;
            default:break;
        }
        return super.onContextItemSelected(item);
    }

}