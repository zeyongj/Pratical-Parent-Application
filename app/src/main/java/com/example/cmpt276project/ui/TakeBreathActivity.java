package com.example.cmpt276project.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.cmpt276project.R;

import java.util.Objects;

public class TakeBreathActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath);

        // enable 'up' button
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);



        registerBeginClicked();
        registerStartClicked();

        numBreathSpinner();


    }




    private void registerBeginClicked() {

        final Button startButton = findViewById(R.id.btn_takeBreathStart);
        final Button btn = findViewById(R.id.btn_takeBreathBegin);
        final View textBox = findViewById(R.id.txt_beginMessage);
        final Spinner numBreathSpinner = findViewById(R.id.spinner_numBreaths);

        startButton.setVisibility(View.INVISIBLE);
        textBox.setVisibility(View.INVISIBLE);
        numBreathSpinner.setVisibility(View.INVISIBLE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textBox.setVisibility(View.VISIBLE);
                numBreathSpinner.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.VISIBLE);

                btn.setVisibility(View.INVISIBLE);

            }
        });
    }


    private void registerStartClicked() {

        Button btn = findViewById(R.id.btn_takeBreathStart);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Start activity inhale
            }
        });



    }

    private void numBreathSpinner() {
        // Number of Breath selecting spinner
        // The following code is quoted from https://developer.android.com/guide/topics/ui/controls/spinner
        Spinner numBreathSpinner = findViewById(R.id.spinner_numBreaths);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.NumberOfBreathSpinner, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        numBreathSpinner.setAdapter(adapter);
        numBreathSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        ((TextView)spinner.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView)spinner.getChildAt(0)).setTextSize(16);

        String text = parent.getItemAtPosition(position).toString();
        // TODO: Save number of breaths into SharedPreference

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}












































