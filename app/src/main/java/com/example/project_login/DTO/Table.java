package com.example.project_login.DTO;

public class Table {
    private String nameTable;
    private String status;
    private String billID;

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

    public Table() {
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
