package com.example.lab2_extra.Model;


public class StudentDTO {
    int id;
    String name;
    String msv;
    String date;
    String type;
    int status;

    public static final String TB_NAME ="student";
    public static final String COL_NAME_ID ="id";
    public static final String COL_NAME_name ="name";
    public static final String COL_NAME_msv ="msv";
    public static final String COL_NAME_date ="date";
    public static final String COL_NAME_type ="type";
    public static final String COL_NAME_status ="status";

    public StudentDTO(int id, String title, String content, String date, String type, int status) {
    }

    public StudentDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsv() {
        return msv;
    }

    public void setMsv(String msv) {
        this.msv = msv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
