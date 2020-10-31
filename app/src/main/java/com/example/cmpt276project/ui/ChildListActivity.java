package com.example.cmpt276project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.cmpt276project.R;

// Activity to handle the Configure my Children part of Iteration 1
public class ChildListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list);

        Toolbar toolbar = findViewById(R.id.childrentoolbar);
        setSupportActionBar(toolbar);
    }
}