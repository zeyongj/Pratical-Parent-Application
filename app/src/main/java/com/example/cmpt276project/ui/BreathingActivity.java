package com.example.cmpt276project.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cmpt276project.R;

import java.util.Objects;

public class BreathingActivity extends AppCompatActivity {

    public static final String BREATH_IN_BUTTON_HELP = "Hold button and breath in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);

        // enable 'up' button
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);

        registerClickedStart();


    }

    private void registerClickedStart() {
        Button btn = findViewById(R.id.btn_inhale);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BreathingActivity.this, BREATH_IN_BUTTON_HELP, Toast.LENGTH_SHORT).show();
            }
        });
    }
}