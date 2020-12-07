package com.example.cmpt276project.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.cmpt276project.R;

import java.util.Objects;

public class TakeBreathActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String PREF_SPINNER = "Number of breath spinner sharedPreference";
    private static final String NUMBER_OF_BREATH_SELECTED = "Number of breath selected";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath);

        // enable 'up' button
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);


        registerStartClicked();

        numBreathSpinner();


    }




    private void registerStartClicked() {

        Button btn = findViewById(R.id.btn_takeBreathStart);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to breathing activity
                Intent intent = new Intent(TakeBreathActivity.this, BreathingActivity.class);
                startActivity(intent);
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
        numBreathSpinner.setSelection(2);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        ((TextView)spinner.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView)spinner.getChildAt(0)).setTextSize(16);

        String text = parent.getItemAtPosition(position).toString();
        saveNumBreath(Integer.parseInt(text));

        // Test if the value is saved
        //Toast.makeText(this, "The number saved is:" + getNumBreath(this), Toast.LENGTH_SHORT).show();

    }

    private void saveNumBreath(int numBreath) {
        SharedPreferences prefs = this.getSharedPreferences(PREF_SPINNER, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUMBER_OF_BREATH_SELECTED, numBreath);
        editor.apply();
    }

    static public int getNumBreath(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_SPINNER, MODE_PRIVATE);
        return prefs.getInt(NUMBER_OF_BREATH_SELECTED, 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}












































