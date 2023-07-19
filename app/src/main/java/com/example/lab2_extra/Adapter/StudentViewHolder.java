package com.example.lab2_extra.Adapter;


import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2_extra.R;

public class StudentViewHolder extends RecyclerView.ViewHolder {
    public TextView txt_name_rcv;
    public TextView txt_msv_rcv;
    public TextView txt_date_rcv;
    public TextView txt_type_rcv;
    public ImageView img_delete;
    public ImageView img_update;
    public CheckBox cb_status;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_name_rcv = itemView.findViewById(R.id.txt_name_rcv);
        txt_msv_rcv = itemView.findViewById(R.id.txt_msv_rcv);
        txt_date_rcv= itemView.findViewById(R.id.txt_date_rcv);
        img_update = itemView.findViewById(R.id.img_update);
        img_delete = itemView.findViewById(R.id.img_delete);
        cb_status = itemView.findViewById(R.id.cb_confirm);
    }
}
