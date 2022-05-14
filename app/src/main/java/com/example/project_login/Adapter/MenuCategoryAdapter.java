package com.example.project_login.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project_login.DTO.Category;
import com.example.project_login.R;

import java.util.List;

public class MenuCategoryAdapter extends BaseAdapter {
    public Context context;
    private int layout;
    private List<Category> listCategory;

    public MenuCategoryAdapter(Context context, int layout, List<Category> listCategory) {
        this.context = context;
        this.layout = layout;
        this.listCategory = listCategory;
    }

    @Override
    public int getCount() {
        return listCategory.size();
    }

    @Override
    public Object getItem(int i) {
        return listCategory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout, null);
        TextView catName = view.findViewById(R.id.category_name);
        ImageView category_img = view.findViewById(R.id.category_img);
        Category category = (Category) getItem(i);
        catName.setText(category.getCatName());
        Glide.with(context).load(category.getImg()).override(category_img.getWidth(), 150).centerCrop().into(category_img);
        return view;
    }
}
