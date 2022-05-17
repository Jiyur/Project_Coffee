package com.example.project_login.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.project_login.DTO.Bill;
import com.example.project_login.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ItemInfoAdapter extends ArrayAdapter<Bill> {
    private ArrayList<Bill> list;
    private Activity context;
    private int resource;

    public ItemInfoAdapter(@NonNull Activity context, int resource, ArrayList<Bill> list) {
        super(context, resource, list);
        this.context=context;
        this.resource=resource;
        this.list=list;
    }




    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View customView=layoutInflater.inflate(this.resource,null);
        TextView billID=(TextView) customView.findViewById(R.id.bill_id_info);
        TextView billTableID=(TextView) customView.findViewById(R.id.bill_table_id);
        TextView billTotal=(TextView) customView.findViewById(R.id.bill_total_info);
        TextView billPayment=(TextView) customView.findViewById(R.id.bill_payment_info);

        Bill bill= (Bill) list.get(i);
        String billIDText="Mã : "+bill.getId();
        billID.setText(billIDText);

        String table="Bàn "+bill.getTable();
        billTableID.setText(table);

        String total=String.valueOf(bill.getTotal())+" VND";
        billTotal.setText(total);
        if(bill.isPayment()){
            billPayment.setText("Đã thanh toán");
        }
        else{
            billPayment.setText("Chưa thanh toán");
            billPayment.setBackground(ContextCompat.getDrawable(context,R.drawable.botron_xanh));

        }
        return customView;

    }
}
