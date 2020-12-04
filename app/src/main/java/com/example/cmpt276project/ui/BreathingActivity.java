package com.example.cmpt276project.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
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
    private static final String INHALE_BUTTON_TEXT = "IN";
    private static final String EXHALE_BUTTON_TEXT = "OUT";
    private static final String ENDING_BUTTON_TEXT = "Good Job";
    private static final String CONTINUE_BREATHING = "More?";



    private MediaPlayer inhaleSound;
    private MediaPlayer exhaleSound;
    private int breathTaken = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);

        // enable 'up' button
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);


        // Set inhaleAnimation TextView invisible
        TextView inhaleText = findViewById(R.id.txt_inhaleText);
        inhaleText.setVisibility(View.INVISIBLE);

        // Set exhaleAnimation TextView invisible
        TextView exhaleText = findViewById(R.id.txt_exhaleText);
        exhaleText.setVisibility(View.INVISIBLE);

        // Set number of breaths in total;
        setRemainingBreaths();

        // Handling breathing sfx
        inhaleSound = MediaPlayer.create(this, R.raw.inhale2);
        exhaleSound = MediaPlayer.create(this, R.raw.exhale2);


        registerClickedStart();


    }

    private void setRemainingBreaths() {
        int totalBreaths = TakeBreathActivity.getNumBreath(BreathingActivity.this);

        String remainingBreaths = Integer.toString(totalBreaths - breathTaken);

        TextView remainingBreathNumbers = findViewById(R.id.txt_remainingBreathNumber);
        remainingBreathNumbers.setText(remainingBreaths);
    }

    private int getRemainingBreaths() {

        TextView tv = findViewById(R.id.txt_remainingBreathNumber);

        return Integer.parseInt(tv.getText().toString());
    }


    // TODO: fix animation attribute to make it look reasonable and good
    private void inhaleAnimationWithSound() {
        TextView inhaleText = findViewById(R.id.txt_inhaleText);
        inhaleText.setVisibility(View.VISIBLE);

        YoYo.with(Techniques.ZoomIn)
                .duration(3000)
                .playOn(inhaleText);

        inhaleSound.start();
    }
    private void exhaleAnimationWithSound() {
        TextView exhaleTv = findViewById(R.id.txt_exhaleText);
        exhaleTv.setVisibility(View.VISIBLE);

        // Set inhale animation TextView invisible to avoid UI overlapping
        TextView inhaleTv = findViewById(R.id.txt_inhaleText);
        inhaleTv.setVisibility(View.INVISIBLE);

        YoYo.with(Techniques.ZoomOut)
                .duration(10000)
                .playOn(exhaleTv);

        exhaleSound.start();
    }



    private void registerClickedStart() {

        registerClickedGeneralButton();

    }




    @SuppressLint("ClickableViewAccessibility")
    private void registerClickedGeneralButton() {
        Button btn = findViewById(R.id.btn_inhale);

        // should use onTouch listener for handling different user movements.
        btn.setOnTouchListener(new View.OnTouchListener() {

            private long buttonDown;

            //flags for conditions
            private boolean isButtonClicked = false;
            private boolean isButtonHeld = false;


            private final Handler handler = new Handler();

            /*----------------------------- Runnable: Messages -----------------------------------*/
            private final Runnable inhaleHelpMessage = new Runnable() {
                @Override
                public void run() {
                    isButtonClicked = true;
                    isButtonHeld = false;
                    Toast.makeText(BreathingActivity.this, BREATH_IN_BUTTON_HELP, Toast.LENGTH_SHORT).show();

                }
            };

            private final Runnable exhaleHelpMessage = new Runnable() {
                @Override
                public void run() {

                    if (!isButtonClicked && isButtonHeld) {
                        Toast.makeText(BreathingActivity.this, EXHALE_REMINDER, Toast.LENGTH_SHORT).show();
                    }
                }
            };

            /*------------------------------ Runnable: Animation ---------------------------------*/

            private final Runnable inhaleAnimation = new Runnable() {
                @Override
                public void run() {
                    isButtonClicked = false;
                    isButtonHeld = true;
                    inhaleAnimationWithSound();
                }
            };

            private final Runnable exhaleAnimation = new Runnable() {
                @Override
                public void run() {
                    exhaleAnimationWithSound();
                }
            };

            /*---------------------------- Runnable: Button Settings -----------------------------*/
            private final Runnable revealInhaleButton = new Runnable() {
                @Override
                public void run() {
                    Button btn = findViewById(R.id.btn_inhale);
                    btn.setText(INHALE_BUTTON_TEXT);
                }
            };

            private final Runnable revealExhaleButton = new Runnable() {
                @Override
                public void run() {
                    Button btn = findViewById(R.id.btn_inhale);
                    btn.setText(EXHALE_BUTTON_TEXT);
                }
            };

            private final Runnable revealFinishButton = new Runnable() {
                @Override
                public void run() {
                    Button btn = findViewById(R.id.btn_inhale);
                    btn.setText(ENDING_BUTTON_TEXT);
                }
            };

            /*------------------------------------------------------------------------------------*/




            /*--------------------------- button on touch event ----------------------------------*/
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN ){

                    buttonDown = System.currentTimeMillis();

                    // Button onHold, show inhale animation
                    handler.postDelayed(inhaleAnimation, 200);


                    // Button continuously held for 10s, show exhale reminder
                    handler.postDelayed(exhaleHelpMessage, 10000);

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

                        // play exhale animation after a short delay.
                        handler.postDelayed(exhaleAnimation, 1000);

                        // update remaining breaths
                        breathTaken += 1;
                        setRemainingBreaths();

                        // if no remaining breaths
                        if (getRemainingBreaths() > 0) {
                            handler.postDelayed(revealInhaleButton, 4000);
                        }
                        else {
                            handler.post(revealFinishButton);

                        }

                        // TODO: "MORE?" button

                    }

                }
                return false;
            }
        });
    }


}









































