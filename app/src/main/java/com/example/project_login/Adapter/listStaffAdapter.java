package com.example.project_login.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.project_login.DTO.User;
import com.example.project_login.R;

import java.util.ArrayList;
import java.util.List;

public class listStaffAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private List<User> listUser;
    private List<User> listUserOld;

    public listStaffAdapter(Context context, int layout, List<User> listNhanVien) {
        this.context = context;
        this.layout = layout;
        this.listUser = listNhanVien;
        this.listUserOld = listNhanVien;
    }
    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int i) {
        return listUser.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        User user = listUser.get(i);
        TextView textView = view.findViewById(R.id.name_txt);
        TextView textView1 = view.findViewById(R.id.chucVu_txt);
        TextView textView2 = view.findViewById(R.id.lienHe_txt);


        textView.setText(user.getFullName());
        textView1.setText(user.getRole());
        textView2.setText(user.getPhone());

        return view;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    listUser = listUserOld;
                }else{
                    List<User> list = new ArrayList<>();
                    for(User user : listUserOld){
                        if(user.getFullName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(user);
                        }
                    }
                    listUser = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listUser;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listUser = (List<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
