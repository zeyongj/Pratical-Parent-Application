package com.example.cmpt276project.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.cmpt276project.model.FlipHistoryManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpt276project.R;

public class FlipCoinHistoryActivity extends AppCompatActivity {

    private FlipHistoryManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // History should be stored in SharedPreferences as user may access when reopen the program.
        manager = FlipHistoryManager.getInstance();


        // Testing to check if history is saved and can be pulled out from SharedPreference.
        Toast.makeText(this, manager.getHistory(0), Toast.LENGTH_SHORT).show();


    }




}