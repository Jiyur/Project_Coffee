package com.example.project_login.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_login.Activities.TableActivity;
import com.example.project_login.DTO.Table;
import com.example.project_login.R;

import java.util.ArrayList;
import java.util.List;

public class TableAdapter extends ArrayAdapter<Table> {
    private TableActivity context;
    private ArrayList<Table> lstTable;

    public TableAdapter(TableActivity context, int layout, ArrayList<Table> lstTable) {
        super(context,layout,lstTable);
    }

//    @Override
//    public int getCount() {
//        return lstTable.size();
//    }
//
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
    private class ViewHolder{
        TextView textviewBan, textviewStatus;
        ImageView imageViewBan;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.layouttable,null);

            holder.textviewBan = (TextView) view.findViewById(R.id.textViewBan);
            holder.textviewStatus = (TextView) view.findViewById(R.id.textViewStatus);
            holder.imageViewBan = (ImageView) view.findViewById(R.id.imageViewBan);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        Table table = getItem(i);
        holder.textviewBan.setText(table.getNameTable());
        holder.textviewStatus.setText(table.getStatus());
        holder.imageViewBan.setImageResource(R.drawable.tableicon);
        return view;
    }
}