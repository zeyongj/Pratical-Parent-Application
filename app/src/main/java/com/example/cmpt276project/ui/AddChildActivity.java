package com.example.cmpt276project.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.ChildrenAdapter;

import java.util.Objects;

public class AddChildActivity extends AppCompatActivity {

    private Children children;
    private ChildrenAdapter childrenAdapter;
    private EditText addChildName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);



        addChildName = findViewById(R.id.txt_enterChildName);

        registerClickedSaveChild();



    }

    private void registerClickedSaveChild() {
        Button btn = findViewById(R.id.btn_saveChild);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChild(children);
            }
        });
    }



    public void addChild(Children children) {
        children.addChild(addChildName.getText().toString());
        childrenAdapter.notifyItemInserted(children.getSize()-1);
    }

}