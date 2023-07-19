package com.example.lab2_extra.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.lab2_extra.DbHelper.DbHeper;
import com.example.lab2_extra.Model.StudentDTO;

import java.util.ArrayList;

public class StudentDAO {
    SQLiteDatabase database;
    DbHeper dbhelper;

    public StudentDAO(Context context) {
        dbhelper = new DbHeper(context);
    }

    public void open() {
        database = dbhelper.getWritableDatabase();

    }

    public void close() {
        dbhelper.close();
    }

    public long Addnew(StudentDTO toDoDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentDTO.COL_NAME_name, toDoDTO.getName());
        contentValues.put(StudentDTO.COL_NAME_msv, toDoDTO.getMsv());
        contentValues.put(StudentDTO.COL_NAME_date, toDoDTO.getDate());
        contentValues.put(StudentDTO.COL_NAME_type, toDoDTO.getType());
        contentValues.put(StudentDTO.COL_NAME_status, 0);
        long res = database.insert(toDoDTO.TB_NAME, null, contentValues);
        return res;

    }

    public int Update(StudentDTO toDoDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentDTO.COL_NAME_name, toDoDTO.getName());
        contentValues.put(StudentDTO.COL_NAME_msv, toDoDTO.getMsv());
        contentValues.put(StudentDTO.COL_NAME_date, toDoDTO.getDate());
        contentValues.put(StudentDTO.COL_NAME_type, toDoDTO.getType());
        contentValues.put(StudentDTO.COL_NAME_status, 0);
        int res = database.update(toDoDTO.TB_NAME, contentValues, "id = " + toDoDTO.getId(), null);
        return res;
    }

    public int UpdateDialog(StudentDTO toDoDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentDTO.COL_NAME_name, toDoDTO.getName());
        contentValues.put(StudentDTO.COL_NAME_msv, toDoDTO.getMsv());
        contentValues.put(StudentDTO.COL_NAME_date, toDoDTO.getDate());
        contentValues.put(StudentDTO.COL_NAME_type, toDoDTO.getType());
        contentValues.put(StudentDTO.COL_NAME_status, 0);
        int res =database.update(toDoDTO.TB_NAME,contentValues,"id= "+toDoDTO.getId(),null);

        return res;
    }
    public int Delete(StudentDTO toDoDTO) {
        return database.delete(toDoDTO.TB_NAME, "id = " + toDoDTO.getId(), null);
    }

    public boolean updateStatusToDO(Integer id, boolean check) {
        int statusValue = check ? 0 : 1;
        ContentValues values = new ContentValues();
        values.put(StudentDTO.COL_NAME_status, statusValue);
        long row = database.update(StudentDTO.TB_NAME, values, "id = ?",new String[]{String.valueOf(id)});
        return  row != -1;
    }

    public ArrayList<StudentDTO> GetAll() {
        ArrayList<StudentDTO> list_todo = new ArrayList<StudentDTO>();
        String[] list_all = new String[]{StudentDTO.COL_NAME_ID, StudentDTO.COL_NAME_name, StudentDTO.COL_NAME_msv, StudentDTO.COL_NAME_date, StudentDTO.COL_NAME_type, StudentDTO.COL_NAME_status};
        Cursor cursor = database.query(StudentDTO.TB_NAME, list_all, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String msv = cursor.getString(2);
            String date = cursor.getString(3);
            String type = cursor.getString(4);
            int status = cursor.getInt(5);
            StudentDTO toDoDTO = new StudentDTO();
            toDoDTO.setId(id);
            toDoDTO.setName(name);
            toDoDTO.setMsv(msv);
            toDoDTO.setDate(date);
            toDoDTO.setType(type);
            toDoDTO.setStatus(status);
            list_todo.add(toDoDTO);
            cursor.moveToNext();

        }
        return list_todo;

    }
}
