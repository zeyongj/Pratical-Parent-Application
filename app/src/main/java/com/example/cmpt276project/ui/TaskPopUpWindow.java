package com.example.cmpt276project.ui;

import android.app.AppComponentFactory;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmpt276project.R;

public class TaskPopUpWindow extends AppCompatActivity {
    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_window);
        myDialog = new Dialog(this);
    }
    public void ShowPopup(View v) {
        TextView txtclose;
        Button btnConfirm;
        Button btnCancel;
        myDialog.setContentView(R.layout.pop_window);
        txtclose =(TextView) myDialog.findViewById(R.id.pop_tv_close);
        txtclose.setText("M");
        btnConfirm = (Button) myDialog.findViewById(R.id.pop_btn_confirm);
        btnCancel = (Button) myDialog.findViewById(R.id.pop_btn_cancel);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
