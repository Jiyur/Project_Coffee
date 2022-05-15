package com.example.project_login.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project_login.DTO.Drinks;
import com.example.project_login.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DrinkAdapter extends BaseAdapter {
    public Context context;
    private int layout;
    private List<Drinks> listDrink;

    public DrinkAdapter(Context context, int layout, List<Drinks> listDrink) {
        this.context = context;
        this.layout = layout;
        this.listDrink = listDrink;
    }

    @Override
    public int getCount() {
        return listDrink.size();
    }

    @Override
    public Object getItem(int i) {
        return listDrink.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout, null);
        TextView nameDrinkText = view.findViewById(R.id.name_txt);
        TextView priceDrinkText = view.findViewById(R.id.price_txt);
        ImageView drinkImg = view.findViewById(R.id.drink_img);
        Drinks drinks = listDrink.get(i);
        nameDrinkText.setText(drinks.getName());
        String price=String.valueOf(drinks.getPrice());
        priceDrinkText.setText(price);

        Glide.with(context).load(drinks.getImage()).override(150, 150).centerCrop().into(drinkImg);

        return view;
    }
}
