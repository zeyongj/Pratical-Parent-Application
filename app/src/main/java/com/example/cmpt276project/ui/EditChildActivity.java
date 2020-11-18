package com.example.cmpt276project.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.ChildrenAdapter;

import java.util.Objects;

public class EditChildActivity extends AppCompatActivity {

    private Children children;
    private int position;
    private ChildrenAdapter childrenAdapter;
    private EditText editChildName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);

        // enable 'up' button
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);

        children= Children.getInstance();
        childrenAdapter = ChildrenAdapter.getInstance();
        editChildName = findViewById(R.id.txt_editChildNameEdit);

        registerClickedOk();
        registerClickedCancel();

    }

// TODO: fix bug when save edited child name with no String

    private void registerClickedOk() {
        Button btn = findViewById(R.id.btn_okEdit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editChildName(children, position);
            }
        });
    }

    private void registerClickedCancel() {
        Button btn = findViewById(R.id.btn_cancelEdit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void editChildName(Children children, int position) {
        // Edit the name of the child at the current position and notify the adapter
        children.editChild(position, editChildName.getText().toString());
        childrenAdapter.notifyDataSetChanged();
    }
}