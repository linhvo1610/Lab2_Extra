package com.example.lab2_extra.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2_extra.DAO.StudentDAO;
import com.example.lab2_extra.Model.StudentDTO;
import com.example.lab2_extra.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {
    Context context;
    ArrayList<StudentDTO> listTodo;
    StudentDAO toDoDAO;


    public StudentAdapter(ArrayList<StudentDTO> list, StudentDAO toDoDAO) {
        this.listTodo = list;
        this.toDoDAO = toDoDAO;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view_of_item = inflater.inflate(R.layout.student_viewholder, parent, false);
        StudentViewHolder toDoViewHolder = new StudentViewHolder(view_of_item);

        return toDoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        final int index = position;
        StudentDTO list = listTodo.get(index);
        holder.txt_name_rcv.setText(list.getName());
        holder.txt_msv_rcv.setText(list.getMsv());
        holder.txt_date_rcv.setText(list.getDate());
        if (listTodo.get(position).getStatus() == 1) {
            holder.cb_status.setChecked(true);
            holder.txt_name_rcv.setPaintFlags(holder.txt_name_rcv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            holder.cb_status.setChecked(false);
            holder.txt_name_rcv.setPaintFlags(holder.txt_name_rcv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDelete(list, index);
            }
        });
        holder.cb_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int id = listTodo.get(holder.getAdapterPosition()).getId();
                boolean check_result = toDoDAO.updateStatusToDO(id, holder.cb_status.isChecked());

                if (check_result) {
                    Toast.makeText(context, "Đã update Status thành công", Toast.LENGTH_SHORT).show();
                    listTodo.clear();
                    listTodo = toDoDAO.GetAll();


                } else {
                    Toast.makeText(context, "Update Status không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.img_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogUpdate(list, index);
            }
        });

    }

    public void UpdateDialogTodo(StudentDTO toDoDTO, int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        EditText ed_dialog_title = view.findViewById(R.id.ed_dialog_name);
        EditText ed_dialog_content = view.findViewById(R.id.ed_dialog_msv);
        EditText ed_dialog_date = view.findViewById(R.id.ed_dialog_date);
        EditText ed_dialog_type = view.findViewById(R.id.ed_dialog_type);
        Button btn_dialog_update = view.findViewById(R.id.btn_dialog_update);
        Button btn_dialog_cancel = view.findViewById(R.id.btn_dialog_cancel);
        ed_dialog_title.setText(toDoDTO.getName());
        ed_dialog_content.setText(toDoDTO.getMsv());
        ed_dialog_date.setText(toDoDTO.getDate());
        ed_dialog_type.setText(toDoDTO.getType());
        ed_dialog_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] ToDo = {"Hard", "Medium", "Easy"};
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                builder.setTitle("Choose Todo Type");
                builder.setItems(ToDo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ed_dialog_type.setText(ToDo[i]);
                    }
                });


                androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn_dialog_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentDTO currentObj = null;
                String name = ed_dialog_title.getText().toString();
                String msv = ed_dialog_content.getText().toString();
                String date = ed_dialog_date.getText().toString();
                String type = ed_dialog_type.getText().toString();

                if (toDoDAO.UpdateDialog(toDoDTO) > 0) {
                    listTodo.set(index, toDoDTO);
                    notifyItemChanged(index);
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }


            }
        });
        alertDialog.show();
    }

    private EditText ed_dialog_name;
    private EditText ed_dialog_msv;
    private EditText ed_dialog_date;
    private EditText ed_dialog_type;
    Button btn_dialog_update;
    Button btn_dialog_cancel;

    public void dialogUpdate(StudentDTO sp, int index) {
        Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_update);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background_dialog);

        ed_dialog_name = dialog.findViewById(R.id.ed_dialog_name);
        ed_dialog_msv = dialog.findViewById(R.id.ed_dialog_msv);
        ed_dialog_date = dialog.findViewById(R.id.ed_dialog_date);
        ed_dialog_type = dialog.findViewById(R.id.ed_dialog_type);
        btn_dialog_update = dialog.findViewById(R.id.btn_dialog_update);
        btn_dialog_cancel = dialog.findViewById(R.id.btn_dialog_cancel);

        ed_dialog_name.setText(sp.getName());
        ed_dialog_msv.setText(sp.getMsv());
        ed_dialog_date.setText(sp.getDate());
        ed_dialog_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] ToDo = {"Hard", "Medium", "Easy"};
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                builder.setTitle("Choose Todo Type");
                builder.setItems(ToDo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ed_dialog_type.setText(ToDo[i]);
                    }
                });


                androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn_dialog_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (toDoDAO.UpdateDialog(sp) > 0) {
                    listTodo.set(index, sp);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    public void dialogDelete(StudentDTO toDoDTO, int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("delete");
        builder.setIcon(R.drawable.ic_baseline_warning_24);
        builder.setMessage("Bạn có muốn xóa " + toDoDTO.getName());
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int res = toDoDAO.Delete(toDoDTO);
                if (res > 0) {
                    listTodo.remove(index);
                    notifyItemRemoved(index);
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public int getItemCount() {
        return listTodo.size();
    }
//    final ArrayList <ToDoDTO> toDoDTOArrayList;
//
//    public TodoAdapter(ArrayList<ToDoDTO> toDoDTOArrayList) {
//        this.toDoDTOArrayList = toDoDTOArrayList;
//    }
//
//    @Override
//    public int getCount() {
//        return toDoDTOArrayList.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return toDoDTOArrayList.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return toDoDTOArrayList.get(i).getId();
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//
//        View itemview;
//        if (view==null){
//            itemview=View.inflate(viewGroup.getContext(), R.layout.layout_listview,null);
//
//        }else itemview=view;
//        ToDoDTO toDoDTO=(ToDoDTO) toDoDTOArrayList.get(i);
//        TextView txt_title = itemview.findViewById(R.id.txt_title);
//        TextView txt_content = itemview.findViewById(R.id.txt_content);
//        TextView txt_date = itemview.findViewById(R.id.txt_date);
//        TextView txt_type = itemview.findViewById(R.id.txt_type);
//        txt_title.setText("Title: "+toDoDTO.getTitle());
//        txt_content.setText("Content: "+toDoDTO.getContent());
//        txt_date.setText("Date: "+toDoDTO.getDate());
//        txt_type.setText("Type: "+toDoDTO.getType());
//        return itemview;
//    } listview

}
