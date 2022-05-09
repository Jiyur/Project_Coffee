package com.example.project_login.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Bill implements Parcelable, Comparator<Bill> {
    String table;
    String time;
    String id;
    Integer total;
    boolean isPayment;
    public ArrayList<Drinks> listDrinks;
    public ArrayList<Integer> listQuantity;

    @Override
    public int compare(Bill bill1, Bill bill2) {
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = new Date(), date2 = new Date();
        try {
            date1 = formatter.parse(bill1.getTime());
            date2 = formatter.parse(bill2.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date1.compareTo(date2);
    }

    public Bill(){
        this.id = "";
        this.table = "";
        this.time = "";
        this.total = 0;
        this.isPayment = false;
        this.listDrinks = new ArrayList<Drinks>();
        this.listQuantity = new ArrayList<Integer>();
    }

    public Bill(String id, String table, String time){
        this.id = id;
        this.table = table;
        this.time = time;
        this.total = 0;
        this.isPayment = false;
        this.listDrinks = new ArrayList<Drinks>();
        this.listQuantity = new ArrayList<Integer>();
    }

    public Bill(String id, String table, String time, boolean isPayment, ArrayList<Drinks> listDrinks, ArrayList<Integer> listQuantity) {
        this.id = id;
        this.table = table;
        this.time = time;
        this.isPayment = isPayment;
        this.listDrinks = listDrinks;
        this.listQuantity = listQuantity;
        this.UpDateTotal();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isPayment() {
        return isPayment;
    }

    public void setPayment(boolean payment) {
        isPayment = payment;
    }

    public Integer getTotal() {
        return total;
    }

    public void UpDateTotal(){
        this.total = 0;
        for(int i=0; i<listDrinks.size(); ++i){
            this.total += (listDrinks.get(i).getPrice() * listQuantity.get(i));
        }
    }

    public void setListDrinks(ArrayList<Drinks> listDrinks) {
        this.listDrinks = listDrinks;
    }

    public void setListQuantity(ArrayList<Integer> listQuantity) {
        this.listQuantity = listQuantity;
    }

    public void addNewDrinks(Drinks drinks, Integer quantity){
        boolean isExist = false;
        for(int i=0; i<listDrinks.size(); ++i){
            if(listDrinks.get(i).getId().equals(drinks.getId())){
                isExist = true;
                listQuantity.set(i, listQuantity.get(i) + quantity);
                break;
            }
        }
        if(!isExist){
            listDrinks.add(drinks);
            listQuantity.add((quantity));
        }
        total += (drinks.getPrice() * quantity);
    }

    protected Bill(Parcel in) {
        table = in.readString();
        time = in.readString();
        isPayment = in.readByte() != 0;
        listDrinks = in.createTypedArrayList(Drinks.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(table);
        dest.writeString(time);
        dest.writeByte((byte) (isPayment ? 1 : 0));
        dest.writeTypedList(listDrinks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };
}
