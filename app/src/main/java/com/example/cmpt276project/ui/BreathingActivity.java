package com.example.cmpt276project.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.cmpt276project.R;

import org.w3c.dom.Text;

import java.util.Objects;

/* Messages worth noticing:
* 1. When first entering the activity, Exhale button should be invisible. Exhale button should only be
*   visible when one inhale process is finished.
* 2. The inhale and exhale processes should take place in the same activity.
* 3. Inhale button onClick should display a help message, on hold should start the inhale process.
* 4. *** Currently i'm not sure about what to do with the exhale button. ***
*
* (this is only my opinion on the ui mechanism, we may
* discuss about how the ui should behave later)
* */



public class BreathingActivity extends AppCompatActivity {

    public static final String BREATH_IN_BUTTON_HELP = "Hold button and breath in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);

        // enable 'up' button
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);

        // Set exhale button invisible when launching the activity
        Button exhaleButton = findViewById(R.id.btn_exhale);
        setButtonVisible(exhaleButton, View.INVISIBLE);

        TextView inhaleText = findViewById(R.id.txt_inhaleText);
        inhaleText.setVisibility(View.INVISIBLE);

        registerClickedStart();


    }

    // TODO: fix animation attribute to make it look reasonable and good
    private void inhaleAnimation() {
        TextView textView = findViewById(R.id.txt_inhaleText);
        YoYo.with(Techniques.ZoomIn)
                .duration(3000)
                .repeat(1)
                .playOn(textView);

        // might need a handler for handling button holding time

    }

    private void setButtonVisible(Button button, int visibility) {
        button.setVisibility(visibility);
    }

    private void registerClickedStart() {
        Button btn = findViewById(R.id.btn_inhale);
        btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(BreathingActivity.this, BREATH_IN_BUTTON_HELP, Toast.LENGTH_SHORT).show();
           }
       });


        // Should use LongClick Listener for "hold"
        // TODO: fix bug when help message displayed when releasing the button.
        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(BreathingActivity.this, "bruh", Toast.LENGTH_SHORT).show();

                TextView inhaleText = findViewById(R.id.txt_inhaleText);
                inhaleText.setVisibility(View.VISIBLE);


                inhaleAnimation();

                return false;
            }
        });


    }
}









































