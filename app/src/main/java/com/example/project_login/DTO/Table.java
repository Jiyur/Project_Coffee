package com.example.project_login.DTO;

import com.google.firebase.database.Exclude;

public class Table {
    @Exclude
    public String id;

    private String billID;
    private String nameTable;
    private String status;

    public Table(){

    }

    public Table(String nameTable, String status, String billID) {
        this.nameTable = nameTable;
        this.status = status;
        this.billID = billID;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }


    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
