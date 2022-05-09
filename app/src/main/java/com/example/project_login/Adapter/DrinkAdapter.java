package com.example.project_login.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_login.DTO.DrinkDTO;
import com.example.project_login.R;

import java.util.List;

public class DrinkAdapter extends BaseAdapter {
    private List<DrinkDTO> drinkDTO;
    private LayoutInflater layoutInflater;
    private Context context;

    public DrinkAdapter(Context context, List<DrinkDTO> drinkDTO) {
        this.context = context;
        this.drinkDTO = drinkDTO;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return drinkDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return drinkDTO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            view = layoutInflater.inflate(R.layout.grid_item, null);
            holder = new ViewHolder();
            holder.drinkImage = (ImageView) view.findViewById(R.id.imgVw_Drink);
            holder.drinkName = (TextView) view.findViewById(R.id.txtVw_drinkId);
            holder.drinkPrice = (TextView) view.findViewById(R.id.txtVw_drinkPrice);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        DrinkDTO drinkDTO = this.drinkDTO.get(position);
        holder.drinkName.setText(drinkDTO.getDrinkId());
        holder.drinkPrice.setText(drinkDTO.getDrinkPrice());

        int imageId = this.getDrawableResIdByName(drinkDTO.getDrinkImage());

        holder.drinkImage.setImageResource(imageId);

        return view;
    }

    public int getDrawableResIdByName(String resName) {
        String pkgName = context.getPackageName();

        int resID = context.getResources().getIdentifier(resName, "drawable", pkgName);
        Log.i("DrinkAdapter", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }

    static class ViewHolder {
        ImageView drinkImage;
        TextView drinkName;
        TextView drinkPrice;
    }
}
