package com.example.cmpt276project.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

    private static final String BREATH_IN_BUTTON_HELP = "Hold button and breath in";
    private static final String EXHALE_REMINDER = "Release button and breath out";

    private int breathTaken = 0;

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

        // Set inhaleAnimation TextView invisible
        TextView inhaleText = findViewById(R.id.txt_inhaleText);
        inhaleText.setVisibility(View.INVISIBLE);

        // Set exhaleAnimation TextView invisible
        TextView exhaleText = findViewById(R.id.txt_exhaleText);
        exhaleText.setVisibility(View.INVISIBLE);

        // Set number of breaths in total;
        setRemainingBreaths();

        registerClickedStart();


    }

    private void setRemainingBreaths() {
        int totalBreaths = TakeBreathActivity.getNumBreath(BreathingActivity.this);

        String remainingBreaths = Integer.toString(totalBreaths - breathTaken);

        TextView remainingBreathNumbers = findViewById(R.id.txt_remainingBreathNumber);
        remainingBreathNumbers.setText(remainingBreaths);
    }

    // TODO: fix animation attribute to make it look reasonable and good
    private void inhaleAnimation() {
        TextView inhaleText = findViewById(R.id.txt_inhaleText);
        inhaleText.setVisibility(View.VISIBLE);

        YoYo.with(Techniques.ZoomIn)
                .duration(3000)
                .playOn(inhaleText);


    }

    // TODO: add inhale sound


    // TODO: change animation so that it make sense (design)
    private void exhaleAnimation() {
        TextView exhaleTv = findViewById(R.id.txt_exhaleText);
        exhaleTv.setVisibility(View.VISIBLE);

        // Set inhale animation TextView invisible to avoid UI overlapping
        TextView inhaleTv = findViewById(R.id.txt_inhaleText);
        inhaleTv.setVisibility(View.INVISIBLE);

        YoYo.with(Techniques.ZoomOut)
                .duration(3000)
                .playOn(exhaleTv);
    }


    // TODO: add exhale sound

    private void setButtonVisible(Button button, int visibility) {
        button.setVisibility(visibility);
    }


    private void registerClickedStart() {

        registerInhaleButton();

    }




    @SuppressLint("ClickableViewAccessibility")
    private void registerInhaleButton() {
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
                    inhaleAnimation();
                }
            };

            private final Runnable revealExhaleButton = new Runnable() {
                @Override
                public void run() {
                    Button exhaleButton = findViewById(R.id.btn_exhale);
                    exhaleButton.setVisibility(View.VISIBLE);

                    // play exhale animation after a short delay.
                    handler.postDelayed(playExhaleAnimation, 1000);
                }
            };


            private final Runnable remindExhaleMessage = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BreathingActivity.this, EXHALE_REMINDER, Toast.LENGTH_SHORT).show();
                }
            };


            private final Runnable playExhaleAnimation = new Runnable() {
                @Override
                public void run() {
                    exhaleAnimation();
                }
            };

            long buttonDown;


            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_DOWN ){

                    buttonDown = System.currentTimeMillis();

                    // Button onHold, show inhale animation
                    handler.postDelayed(inhaleAnimation, 200);




                    // TODO: fix bug when releasing button, the message still shows
                    // Button continuously held for 10s, show exhale reminder
                    handler.postDelayed(remindExhaleMessage, 10000);

                }

                else if (event.getAction() == MotionEvent.ACTION_UP) {

                    handler.removeCallbacks(inhaleAnimation);

                    // inhale button "onClick", display inhale help message
                    if ((System.currentTimeMillis() - buttonDown) < 200) {
                        handler.post(inhaleHelpMessage);
                    }

                    // Button released after holding for 3s, stop inhaling, reveal exhale button
                    if ((System.currentTimeMillis() - buttonDown) >= 3000 ) {
                        handler.post(revealExhaleButton);
                    }

                }
                return false;
            }
        });
    }


    private void registerExhaleButton() {
        Button inhaleButton = findViewById(R.id.btn_inhale);
        Button exhaleButton = findViewById(R.id.btn_exhale);
        TextView exhaleTextView = findViewById(R.id.txt_exhaleText);

//        exhaleTextView.setVisibility(View.VISIBLE);
//        exhaleAnimation();

        exhaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Done exhale: stop animation; stop sound
            }
        });
    }
}









































