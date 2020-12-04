package com.example.cmpt276project.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.cmpt276project.R;

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
    public static final String EXHALE_REMINDER = "Release button and breath out";
    boolean buttonPressed;

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
                .playOn(textView);


    }

    private void setButtonVisible(Button button, int visibility) {
        button.setVisibility(visibility);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void registerClickedStart() {
        Button btn = findViewById(R.id.btn_inhale);


       // should use onTouch listener for handling different user movements.
        btn.setOnTouchListener(new View.OnTouchListener() {

            private final Handler handler = new Handler();


            private final Runnable inhaleHelpMessage = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BreathingActivity.this, BREATH_IN_BUTTON_HELP, Toast.LENGTH_SHORT).show();

                }
            };


            private final Runnable inhaleAnimation = new Runnable() {
                @Override
                public void run() {

                        // play inhale animation
                        TextView inhaleText = findViewById(R.id.txt_inhaleText);
                        inhaleText.setVisibility(View.VISIBLE);
                        inhaleAnimation();

                }
            };

            private final Runnable revealExhaleButton = new Runnable() {
                @Override
                public void run() {
                    Button exhaleButton = findViewById(R.id.btn_exhale);
                    exhaleButton.setVisibility(View.VISIBLE);
                }
            };


            private final Runnable remindExhaleMessage = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BreathingActivity.this, EXHALE_REMINDER, Toast.LENGTH_SHORT).show();
                }
            };

            long buttonDown;


            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_DOWN ){

                    buttonDown = System.currentTimeMillis();

                    // Button onHold, show inhale animation
                    handler.postDelayed(inhaleAnimation, 200);


                    // Button continuously held for 10s, show exhale reminder
                    handler.postDelayed(remindExhaleMessage, 10000);

                }

                else if (event.getAction() == MotionEvent.ACTION_UP) {

                    handler.removeCallbacks(inhaleAnimation);

                    // inhale button "onClick", display inhale help message
                    if ((System.currentTimeMillis() - buttonDown) < 200) {
                        handler.post(inhaleHelpMessage);
                    }

                    // Button released after holding for 3s, stop inhaling
                    if ((System.currentTimeMillis() - buttonDown) >= 3000 ) {
                        handler.post(revealExhaleButton);
                    }

                }
                return false;
            }
        });
    }
}









































