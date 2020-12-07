package com.example.cmpt276project.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.AlarmNotificationService;


// Activity to handle the Timeout Timer part of iteration 1 for the project
// Allows the user to choose the number of minutes in the timer
// Displays a notification, plays an alarm, and vibrates when the timer is up
@RequiresApi(api = Build.VERSION_CODES.O)
public class TimeoutTimerActivity extends AppCompatActivity {
    private String ALARM_TIME_INT = "Countdown timer value for service";
    private String TIME_SPEED_MULTIPLIER = "Multiplier for the speed of the CountDownTimer";

    // Initialize the variables
    String id ="channel_1";//id of channel
    String description = "123";//Description information of channel

    int importance = NotificationManager.IMPORTANCE_LOW;//The Importance of channel
//    NotificationChannel channel = new NotificationChannel(id, "123", importance);//Generating channel
    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private TextView mTimeSpeed;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Button[] mButtons = new Button[5];
    private ProgressBar mProgress;
    int progress;


    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mCountDownTimeInMillis;
    private static long mStartTimeInMillis;
    private static long mTimeLeftInMillis;
    int proportionOfRunning = 0;
    private long mEndTime;
    private double timeSpeedMultiplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout_timer);

        // Set back button on toolbar
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);


        Toast.makeText(this, R.string.timerInstruction, Toast.LENGTH_LONG).show();


        setButtonsAndViews();
        setCertainTime();

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(TimeoutTimerActivity.this, R.string.timerInputEmpty,Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(input) * 60000; // To minutes
                if (millisInput == 0) {
                    Toast.makeText(TimeoutTimerActivity.this, R.string.timerInputNoPositive,Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning){
                    pauseTimer();
                    stopTimerService();
                }else{
                    startTimer();
                    Intent intent = new Intent(TimeoutTimerActivity.this, AlarmNotificationService.class);
                    intent.putExtra(ALARM_TIME_INT, mTimeLeftInMillis);
                    startService(intent);
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
                stopTimerService();
            }
        });
//        updateCountDownText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timer_speed_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.twentyfive_speed:
                changeCountDownSpeed(0.25);
                mTimeSpeed.setText(getString(R.string.current_time_speed, getString(R.string.twentyfive_percent)));
                return true;
            case R.id.fifty_speed:
                changeCountDownSpeed(0.5);
                mTimeSpeed.setText(getString(R.string.current_time_speed, getString(R.string.fifty_percent)));
                return true;
            case R.id.seventyfive_speed:
                changeCountDownSpeed(0.75);
                mTimeSpeed.setText(getString(R.string.current_time_speed, getString(R.string.seventyfive_percent)));
                return true;
            case R.id.hundred_speed:
                changeCountDownSpeed(1);
                mTimeSpeed.setText(getString(R.string.current_time_speed, getString(R.string.hundred_percent)));
                return true;
            case R.id.twohundred_speed:
                changeCountDownSpeed(2);
                mTimeSpeed.setText(getString(R.string.current_time_speed, getString(R.string.twohundred_percent)));
                return true;
            case R.id.threehundred_speed:
                changeCountDownSpeed(3);
                mTimeSpeed.setText(getString(R.string.current_time_speed, getString(R.string.threehundred_percent)));
                return true;
            case R.id.fourhundred_speed:
                changeCountDownSpeed(4);
                mTimeSpeed.setText(getString(R.string.current_time_speed, getString(R.string.fourhundred_percent)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeCountDownSpeed(double multiplier) {
        mCountDownTimeInMillis = (long) (mTimeLeftInMillis / multiplier);
        timeSpeedMultiplier = multiplier;
        mCountDownTimer.cancel();
        stopTimerService();
        startTimer();
        Intent intent = new Intent(TimeoutTimerActivity.this, AlarmNotificationService.class);
        intent.putExtra(ALARM_TIME_INT, mTimeLeftInMillis);
        intent.putExtra(TIME_SPEED_MULTIPLIER, timeSpeedMultiplier);
        startService(intent);
    }

    private void setButtonsAndViews() {
        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonSet = findViewById(R.id.button_set);
        Button mButtonSet1Min = findViewById(R.id.button_set1min);
        Button mButtonSet2Min = findViewById(R.id.button_set2min);
        Button mButtonSet3Min = findViewById(R.id.button_set3min);
        Button mButtonSet5Min = findViewById(R.id.button_set5min);
        Button mButtonSet10Min = findViewById(R.id.button_set10min);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mButtons = new Button[]{mButtonSet1Min, mButtonSet2Min, mButtonSet3Min, mButtonSet5Min, mButtonSet10Min};

        //Initialize progress bar
        mProgress = findViewById(R.id.timer_progressBar);
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if( proportionOfRunning <= 100) {
//                    mProgress.setProgress(proportionOfRunning);
//                    handler.postDelayed(this,200);
//                }
//                else{
//                    handler.removeCallbacks(this);
//                }
//            }
//        },200);
        mProgress.setVisibility(View.VISIBLE);
        mProgress.setProgress(0);
        mProgress.setMax(100);
    }

    private void setCertainTime() {
        for (int i = 0; i < 5; i++) {
            final int index = i + 1;
            mButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long multiplier = index;
                    if (index == 4) {
                        multiplier = 5;
                    } else if (index == 5) {
                        multiplier = 10;
                    }
                    setTime(multiplier * 60000);

                    mEditTextInput.setText("");
                }
            });
        }
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyBoard();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        final int numberOfSeconds = (int) (mStartTimeInMillis/1000);
//        String checkSeconds = "Number of seconds is " + numberOfSeconds;
//        Toast.makeText(this, checkSeconds, Toast.LENGTH_SHORT).show();
        final double factor = 100.0 / numberOfSeconds;


        mCountDownTimer = new CountDownTimer(mCountDownTimeInMillis, (long) (1000/timeSpeedMultiplier)) { //Every 1 second
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis -= 1000;
                updateCountDownText();
                int secondsRemaining = (int) (l / 1000);
//                String CheckRemaining = "Number of seconds is " + secondsRemaining;
//                Toast.makeText(getApplicationContext(), CheckRemaining, Toast.LENGTH_SHORT).show();
                int progressPercentage = (int) Math.floor((numberOfSeconds - secondsRemaining) * factor);
//                String checkFactor = "Now the factor is " + factor;
//                Toast.makeText(getApplicationContext(), checkFactor, Toast.LENGTH_SHORT).show();
                mProgress.setProgress(progressPercentage);
//                String checkProgressPercentage = "Number of progress percentage is " + progressPercentage;
//                Toast.makeText(getApplicationContext(), checkProgressPercentage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                pauseTimer();
                mProgress.setProgress(100);
                resetTimer();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
        mProgress.clearAnimation();
    }

    private void resetTimer() {
        if (mTimerRunning) {
            mCountDownTimer.cancel();
            mTimerRunning = false;
        }
        mCountDownTimeInMillis = mStartTimeInMillis;
        mTimeLeftInMillis = mStartTimeInMillis;
        timeSpeedMultiplier = 1;
        updateCountDownText();
        updateWatchInterface();
        mProgress.setProgress(0);
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    getString(R.string.timerWithHours),hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    getString(R.string.timerWithoutHours),minutes,seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface(){
        if (mTimerRunning) {
            mButtonStartPause.setText(R.string.Pause);
        }
        else{
            mButtonStartPause.setText(R.string.Start);
            if(mTimeLeftInMillis < 1000) {
                //Timer needs to be reset as it reaches 0.
                resetTimer();
            }
        }
    }



    private void closeKeyBoard () {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis",mStartTimeInMillis);
        editor.putLong("millisLeft",mTimeLeftInMillis);
        editor.putBoolean("timerRunning",mTimerRunning);
        editor.putLong("endTime",mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning",false);
        mCountDownTimeInMillis = mTimeLeftInMillis;
        timeSpeedMultiplier = 1;

        updateCountDownText();
        updateWatchInterface();

        if(mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }

    private void stopTimerService() {
        Intent intent = new Intent(TimeoutTimerActivity.this, AlarmNotificationService.class);
        stopService(intent);
    }
}