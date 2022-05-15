package com.example.project_login.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_login.Activities.Bill.BillActivity;
import com.example.project_login.Activities.Order.MenuOrderActivity;
import com.example.project_login.Activities.Order.OrderActivity;
import com.example.project_login.Activities.Table.table_management;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemMenuOrderAdapter extends BaseAdapter {
    Context context;
    ArrayList<Drinks> arrayListDrinks;

    public ItemMenuOrderAdapter(Context context, ArrayList<Drinks> arrayListDrinks){
        this.context = context;
        this.arrayListDrinks = arrayListDrinks;
    }

    @Override
    public int getCount() {
        return arrayListDrinks.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListDrinks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_menu_order, null);
        }

        Drinks drinks = arrayListDrinks.get(i);

        TextView txtName = (TextView) view.findViewById(R.id.textviewname);
        TextView txtCategory = (TextView) view.findViewById(R.id.textviewcategory);
        TextView txtPrice = (TextView) view.findViewById(R.id.textviewprice);
        ImageView imgDrink = (ImageView) view.findViewById(R.id.drinks_image);
        Button btnOrder = (Button) view.findViewById(R.id.buttonorder);

        txtName.setText(drinks.getName());
        txtCategory.setText("Category:   " + drinks.getCategory());
        txtPrice.setText("Price:   " + drinks.getPrice().toString() +"Đ");

        Picasso.with(context).load(drinks.getImage())
                .fit()
                .into(imgDrink);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuOrderActivity menuOrderActivity = (MenuOrderActivity) context;
                menuOrderActivity.OpenOrderACtivity(drinks.getId());
            }
        });

        return view;
    }
}
