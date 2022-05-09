package com.example.project_login.Activities.StaffManagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project_login.Adapter.listStaffAdapter;
import com.example.project_login.DAO.UserDAO;
import com.example.project_login.DTO.User;
import com.example.project_login.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class management_staff extends AppCompatActivity {
    private SearchView searchView;
    private DatabaseReference mDatabase;
    Toolbar toolbar;
    ListView listView;
    List<User> listnv;
    listStaffAdapter adapter;
    Button add_btn;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_staff);

        toolbar = findViewById(R.id.listStaff_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.list_staff);
        add_btn = findViewById(R.id.add_btn);
        mDatabase = UserDAO.getMyDatabase();

        showListData();
    }

    public void addOnclick(View view) {
        Intent intent = new Intent(management_staff.this, add_staff.class);
        startActivity(intent);
    }

    public void showListData(){
        listnv = new ArrayList<User>();
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                listnv.add(user);
                adapter = new listStaffAdapter(management_staff.this, R.layout.list_staff_item, listnv);
                listView.setAdapter(adapter);
                registerForContextMenu(listView);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user == null || listnv == null || listnv.isEmpty()){
                    return;
                }
                for(int i = 0; i < listnv.size(); i++){
                    if(user.getPhone() == listnv.get(i).getPhone()){
                        listnv.remove(listnv.get(i));
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menutoolbar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_item).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        user = (User) adapter.getItem(pos);
        switch (item.getItemId()){
            case R.id.delete_item:
                createDialogDelete(user);
                break;
            case R.id.edit_item:
                Intent intent = new Intent(management_staff.this, edit_staff.class);
                intent.putExtra("User", user);
                startActivity(intent);
                break;
            case R.id.call_item:
                ActivityCompat.requestPermissions(management_staff.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void createDialogDelete(User user){
        AlertDialog.Builder builder = new AlertDialog.Builder(management_staff.this);
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference database = UserDAO.getMyDatabase();
                database.child(user.getPhone()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if(error == null){
                            Toast.makeText(management_staff.this, "Delete success", Toast.LENGTH_SHORT ).show();
                        }else{
                            Toast.makeText(management_staff.this, "Delete fail", Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(management_staff.this, "Cancel delete", Toast.LENGTH_SHORT ).show();
            }
        });
        builder.create().show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:{
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri number = Uri.parse("tel:"+user.getPhone());
                    intent.setData(number);
                    startActivity(intent);
                }else{
                    Toast.makeText(management_staff.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}