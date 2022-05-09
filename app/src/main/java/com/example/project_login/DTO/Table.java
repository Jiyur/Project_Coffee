package com.example.project_login.DTO;

public class Table {
    private String idTable;
    private int idBill;
    private String status;

    public Table() {
    }

    public Table(String status) {
        this.status = status;
        this.idTable = "";
        this.idBill = 0;
    }

    public Table(String idTable, String status) {
        this.idTable = idTable;
        this.status = status;
    }

    public String getIdTable() {
        return idTable;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
