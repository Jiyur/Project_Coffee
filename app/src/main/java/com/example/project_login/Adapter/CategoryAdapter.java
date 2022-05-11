package com.example.project_login.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_login.DTO.CategoryDTO;
import com.example.project_login.R;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private List<CategoryDTO> categoryDTOList;
    private LayoutInflater layoutInflater;
    private Context context;

    public CategoryAdapter(Context context, List<CategoryDTO> categoryDTOList) {
        this.context = context;
        this.categoryDTOList = categoryDTOList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return categoryDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            view = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.catImage = (ImageView) view.findViewById(R.id.imgVw_CategoryItem);
            holder.catName = (TextView) view.findViewById(R.id.txtVw_CategoryItem);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CategoryDTO categoryDTO = this.categoryDTOList.get(position);

        int imageId = this.getDrawableResIdByName(categoryDTO.getCatName());

        holder.catImage.setImageResource(imageId);
        return view;
    }

    public int getDrawableResIdByName(String resName) {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName, "drawable", pkgName);
        Log.i("CategoryListView", "Res Name:" + resName + "==> Res ID = " + resID);
        return resID;
    }

    static class ViewHolder {
        ImageView catImage;
        TextView catName;
    }
}
