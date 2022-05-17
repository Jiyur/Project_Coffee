package com.example.project_login.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_login.Activities.Bill.BillActivity;
import com.example.project_login.Activities.Global;
import com.example.project_login.Activities.Order.MenuOrderActivity;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.R;
import com.squareup.picasso.Picasso;


import java.text.DecimalFormat;
import java.util.ArrayList;


public class ItemBillAdapter extends BaseAdapter {
    Context context;
    ArrayList<Drinks> arrayListDrinks;
    ArrayList<Integer> arrayListQuantity;

    public ItemBillAdapter(Context context, ArrayList<Drinks> arrayListDrinks, ArrayList<Integer> arrayListQuantity) {
        this.context = context;
        this.arrayListDrinks = arrayListDrinks;
        this.arrayListQuantity = arrayListQuantity;
    }

    @Override
    public int getCount() {
        return arrayListDrinks.size();
    }

    @Override
    public Drinks getItem(int i) {
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
            view = inflater.inflate(R.layout.item_bill, null);
        }

        Drinks drinks = arrayListDrinks.get(i);
        Integer quantity = arrayListQuantity.get(i);

        TextView txtName = (TextView) view.findViewById(R.id.textviewname);
        TextView txtPrice = (TextView) view.findViewById(R.id.textviewprice);
        TextView txtQuantity = (TextView) view.findViewById(R.id.textviewquantity);
        ImageView imgDrink = (ImageView) view.findViewById(R.id.imageviewdrink);
        ImageButton btnEdit = (ImageButton) view.findViewById(R.id.buttonedit);

        txtName.setText(drinks.getName());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtQuantity.setText(decimalFormat.format(quantity));
        txtPrice.setText(decimalFormat.format(drinks.getPrice()));
        if(Global.checkBill){
            btnEdit.setVisibility(View.INVISIBLE);
        }else{
            btnEdit.setVisibility(View.VISIBLE);
        }

        Picasso.with(context).load(drinks.getImage())
                .fit()
                .into(imgDrink);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BillActivity billActivity = (BillActivity) context;
                billActivity.EditBill(drinks.getId());
            }
        });

        return view;
    }
}