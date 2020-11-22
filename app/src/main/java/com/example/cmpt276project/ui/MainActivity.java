package com.example.cmpt276project.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.MainMenuAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;


// Main activity page for when the program starts
// Lets the user go to the different activity when the respective buttons are clicked
public class MainActivity extends AppCompatActivity {

    MainMenuAdapter mainMenuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buildMenuView();
    }

    public void buildMenuView() {
        // Find ID of RecyclerView
        RecyclerView menuRecyclerView = findViewById(R.id.mainmenuview);
        menuRecyclerView.setHasFixedSize(true);

        // Set Layout for the RecyclerView
        RecyclerView.LayoutManager menuLayoutManager = new LinearLayoutManager(this);
        menuRecyclerView.setLayoutManager(menuLayoutManager);

        // Set Adapter for the RecyclerView
        mainMenuAdapter = new MainMenuAdapter(this);
        menuRecyclerView.setAdapter(mainMenuAdapter);
    }
}