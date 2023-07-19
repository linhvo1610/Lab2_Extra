package com.example.lab2_extra;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lab2_extra.Adapter.StudentAdapter;
import com.example.lab2_extra.DAO.StudentDAO;
import com.example.lab2_extra.Model.StudentDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    StudentDAO toDoDAO;
    StudentAdapter todoAdapter;
    ListView lv_todo;
    RecyclerView todo_rcv;
    ArrayList<StudentDTO> arrayList;
    StudentDTO currentObj = null;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);

        toDoDAO = new StudentDAO(this);
        toDoDAO.open();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewdialog=inflater.inflate(R.layout.dialog_add,null);
                dialog.setView(viewdialog);
                EditText input_title,input_content,input_date,input_type;
                input_title = viewdialog.findViewById(R.id.input_name);
                input_content = viewdialog.findViewById(R.id.input_msv);
                input_date = viewdialog.findViewById(R.id.input_date);
                input_type = viewdialog.findViewById(R.id.input_type);
                AlertDialog alertDialog = dialog.create();
                input_type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] ToDo = {"Hard","Medium","Easy"};
                        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Choose Todo Type");
                        builder.setItems(ToDo, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                input_type.setText(ToDo[i]);
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                Button btn_add_dialog = viewdialog.findViewById(R.id.btn_dialog_add);
                Button btn_cancel = viewdialog.findViewById(R.id.btn_dialog_cancel_add);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                btn_add_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StudentDTO toDoDTO= new StudentDTO();
                        toDoDTO.setName(input_title.getText().toString());
                        toDoDTO.setMsv(input_content.getText().toString());
                        toDoDTO.setDate(input_date.getText().toString());
                        toDoDTO.setType(input_type.getText().toString());
                        long ketqua = toDoDAO.Addnew(toDoDTO);
                        if(ketqua>0){
                            arrayList.clear();
                            arrayList.addAll(toDoDAO.GetAll());
                            todoAdapter.notifyDataSetChanged();
                            input_title.setText("");
                            input_content.setText("");
                            input_date.setText("");
                            input_type.setText("");
                            alertDialog.dismiss();

                            Toast.makeText(getApplicationContext(),"Them moi thanh cong"+ketqua,Toast.LENGTH_LONG).show();
                        }else Toast.makeText(getApplicationContext(),"Them moi that bai"+ketqua,Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.show();


            }
        });
        arrayList = toDoDAO.GetAll();
        todoAdapter = new StudentAdapter(arrayList,toDoDAO);
//        lv_todo = findViewById(R.id.lv_todo);
        todo_rcv = findViewById(R.id.rcl_todo);
        todo_rcv.setAdapter(todoAdapter);
//        lv_todo.setAdapter(todoAdapter);
//        lv_todo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                currentObj = arrayList.get(i);
//                input_title.setText(currentObj.getTitle());
//                input_content.setText(currentObj.getContent());
//                input_date.setText(currentObj.getDate());
//                input_type.setText(currentObj.getType());
//                return false;
//            }
//        });
    }

//    public void AddTodo(View view){
//
//
//    }
//    public void UpdateTodo(View view){
//        String new_input_title = input_title.getText().toString();
//        String new_input_content = input_content.getText().toString();
//        String new_input_date = input_date.getText().toString();
//        String new_input_type = input_type.getText().toString();
//        if(currentObj!=null&&(!currentObj.getTitle().equalsIgnoreCase(new_input_title)||!currentObj.getContent().equalsIgnoreCase(new_input_content)
//                ||!currentObj.getDate().equalsIgnoreCase(new_input_date)||!currentObj.getType().equalsIgnoreCase(new_input_type))){
//            currentObj.setTitle(new_input_title);
//            currentObj.setContent(new_input_content);
//            currentObj.setDate(new_input_date);
//            currentObj.setType(new_input_type);
//            int res = toDoDAO.Update(currentObj);
//            if(res>0){
//                input_title.setText("");
//                input_content.setText("");
//                input_date.setText("");
//                input_type.setText("");
//                arrayList.clear();
//                arrayList.addAll(toDoDAO.GetAll());
//                todoAdapter.notifyDataSetChanged();
//                Toast.makeText(this,"Cập nhật thành công",Toast.LENGTH_LONG).show();
//                currentObj = null;
//            }else{
//                Toast.makeText(this,"Lỗi",Toast.LENGTH_LONG).show();
//            }
//        }else Toast.makeText(this,"Không có gì để cập nhật  ",Toast.LENGTH_LONG).show();
//    }
//
//    public void DeleteTodo(View view){
//        if(currentObj!=null){
//            int res = toDoDAO.Delete(currentObj);
//            if(res>0){
//                arrayList.clear();
//                arrayList.addAll(toDoDAO.GetAll());
//                todoAdapter.notifyDataSetChanged();
//                input_title.setText("");
//                input_content.setText("");
//                input_date.setText("");
//                input_type.setText("");
//                Toast.makeText(this,"Xóa thành công",Toast.LENGTH_LONG).show();
//                currentObj = null;
//            }else {
//                Toast.makeText(this, "Lỗi", Toast.LENGTH_LONG).show();
//            }
//        }else Toast.makeText(this,"Bấm và giữ 1 bản khi nào đó trước khi xóa  ",Toast.LENGTH_LONG).show();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toDoDAO.close();
    }
}