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
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;

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

        // Set inhaleHelpMessage TextView invisible
        TextView inhaleHelpMessage = findViewById(R.id.txt_inhaleHelpMessage);
        inhaleHelpMessage.setVisibility(View.INVISIBLE);

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

    private int setRemainingBreaths() {
        int totalBreaths = TakeBreathActivity.getNumBreath(BreathingActivity.this);

        String remainingBreaths = Integer.toString(totalBreaths - breathTaken);

        TextView remainingBreathNumbers = findViewById(R.id.txt_remainingBreathNumber);
        remainingBreathNumbers.setText(remainingBreaths);

        return Integer.parseInt(remainingBreaths);
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
                .duration(10000)
                .playOn(inhaleText);

        inhaleSound.start();
    }

    private void inhaleAnimationReset() {
        TextView inhaleText = findViewById(R.id.txt_inhaleText);
        inhaleText.setVisibility(View.INVISIBLE);
        inhaleText.clearAnimation();

        inhaleSound.pause();
        inhaleSound.seekTo(0);

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


            //flags for conditions
            private boolean isButtonClicked = false;
            private boolean isButtonHeld = false;


            private final Handler handler = new Handler();

            /*----------------------------- Runnable: Messages -----------------------------------*/

            private final Runnable exhaleReminder = new Runnable() {
                @Override
                public void run() {

                    if (isButtonHeld) {
                        Toast.makeText(BreathingActivity.this, EXHALE_REMINDER, Toast.LENGTH_SHORT).show();
                    }
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
                if (isButtonHeld) {
                        Button btn = findViewById(R.id.btn_inhale);
                        btn.setText(EXHALE_BUTTON_TEXT);
                    }
                }
            };

            private final Runnable enableButton = new Runnable() {
                @Override
                public void run() {
                    Button btn = findViewById(R.id.btn_inhale);
                    btn.setEnabled(true);
                }
            };

            private final Runnable disableButton = new Runnable() {
                @Override
                public void run() {
                    Button btn = findViewById(R.id.btn_inhale);
                    btn.setEnabled(false);
                }
            };


            private final Runnable revealFinishButton = new Runnable() {
                @Override
                public void run() {

                    if (!isButtonClicked && isButtonHeld) {
                        Button btn = findViewById(R.id.btn_inhale);
                        btn.setText(ENDING_BUTTON_TEXT);
                    }
                }
            };

            /*------------------------------------------------------------------------------------*/




            /*--------------------------- button on touch event ----------------------------------*/
            long t1; long t2;

            private boolean stateInhale = true;


            @Override
            public boolean onTouch(View v, MotionEvent event) {


                stateInhale= true;


                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    if (stateInhale) {

                        t1 = System.currentTimeMillis();

                        inhaleAnimationWithSound();

                        // Button continue hold for 3s, change button text to "OUT"
                        handler.postDelayed(revealExhaleButton, 3000);
                        isButtonHeld = true;

                        // Button continue hold for 10s, stop animation and sound, add reminder
                        handler.postDelayed(exhaleReminder, 10000);
                        isButtonHeld = true;

                    }


                }

                else if (event.getAction() == MotionEvent.ACTION_UP) {

                    if (stateInhale && isButtonHeld) {

                        t2 = System.currentTimeMillis();

                        // update button state
                        isButtonHeld = false;

                        if ((t2 - t1) < 200) {

                            inhaleAnimationReset();

                        }

                        else if ((t2 - t1) >= 200 && (t2 - t1) < 3000) {

                            // Button released before 3s continuously, reset animation and sound.
                            inhaleAnimationReset();

                        }
                        else if ((t2 - t1) >= 3000 && (t2 - t1) < 10000) {

                            // After 3s, upon releasing button, stop animation and sound, to exhale
                            inhaleAnimationReset();


                            // Exhale
                            stateInhale = false;

                            // Disable button
                            handler.post(disableButton);

                            exhaleAnimationWithSound();



                            // Update remaining breath number
                            breathTaken++;

                            // After exhale 3s, with remaining breath, change button to "IN", update count, enable button.
                            setRemainingBreaths();

                            if (getRemainingBreaths() > 0 ) {
                                handler.postDelayed(enableButton, 3000);

                                handler.postDelayed(revealInhaleButton, 3000);

                            }

                            //TODO: good job not displayed




                        }
                    }

                }

                    return false;

            }
        });
    }


}









































