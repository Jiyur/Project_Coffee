package com.example.project_login.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_login.DTO.Table;
import com.example.project_login.DTO.User;
import com.example.project_login.R;

import org.w3c.dom.Text;

import java.util.List;

public class TableAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Table> listTable;

    public TableAdapter(Context context, int layout, List<Table> listTable) {
        this.context = context;
        this.layout = layout;
        this.listTable = listTable;
    }

    @Override
    public int getCount() {
        return listTable.size();
    }

    @Override
    public Object getItem(int i) {
        return listTable.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout, null);
        Table table = listTable.get(i);
        TextView tableTitleText = view.findViewById(R.id.title_table);
        ImageView tableImg = view.findViewById(R.id.table_img);

        tableTitleText.setText("Table " + String.valueOf(i + 1));
        tableImg.setImageResource(R.drawable.icon_table_empty);


        return view;
    }
}
