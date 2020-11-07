package com.example.cmpt276project.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.AlarmNotificationService;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TimeoutTimer extends AppCompatActivity {
    private String ALARM_TIME_INT = "Countdown timer value for service";

    // Initialize the variables
    String id ="channel_1";//id of channel
    String description = "123";//Description information of channel

    int importance = NotificationManager.IMPORTANCE_LOW;//The Importance of channel
//    NotificationChannel channel = new NotificationChannel(id, "123", importance);//Generating channel
    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Button[] mButtons = new Button[5];

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout_timer);

        setButtonsAndViews();
        setCertainTime();

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(TimeoutTimer.this,"Field cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(input) * 60000; // To minutes
                if (millisInput == 0) {
                    Toast.makeText(TimeoutTimer.this,"Please enter a positive number",Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(TimeoutTimer.this, AlarmNotificationService.class);
                    intent.putExtra(ALARM_TIME_INT, mTimeLeftInMillis);
                    startService(intent);
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
//        updateCountDownText();
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
    }

    private void setCertainTime() {
        for (int i = 0; i<5; i++) {
            final int index = i+1;
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
/*                  Set 1 minute timer to 6 seconds for testing purposes
                    if (index == 1) {
                        setTime(6000);
                    }*/
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

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) { //Every 1 second
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                pauseTimer();
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
    }

    private void resetTimer() {
        if (mTimerRunning) {
            mCountDownTimer.cancel();
            mTimerRunning = false;
        }
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
        stopTimerService();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d",hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d",minutes,seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface(){
        if (mTimerRunning) {
//            mEditTextInput.setVisibility(View.INVISIBLE);
//            mButtonSet.setVisibility(View.INVISIBLE);
//            mButtonSet1Min.setVisibility(View.INVISIBLE);
//            mButtonSet2Min.setVisibility(View.INVISIBLE);
//            mButtonSet3Min.setVisibility(View.INVISIBLE);
//            mButtonSet5Min.setVisibility(View.INVISIBLE);
//            mButtonSet10Min.setVisibility(View.INVISIBLE);
//            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText(R.string.Pause);
        }
        else{
//            mEditTextInput.setVisibility(View.VISIBLE);
//            mButtonSet.setVisibility(View.VISIBLE);
//            mButtonSet1Min.setVisibility(View.VISIBLE);
//            mButtonSet2Min.setVisibility(View.VISIBLE);
//            mButtonSet3Min.setVisibility(View.VISIBLE);
//            mButtonSet5Min.setVisibility(View.VISIBLE);
//            mButtonSet10Min.setVisibility(View.VISIBLE);
            mButtonStartPause.setText(R.string.Start);

            if(mTimeLeftInMillis < 1000) {
                //Play notification
                resetTimer();

            }else{
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

//            if(mTimeLeftInMillis < mStartTimeInMillis) {
//                mButtonReset.setVisibility(View.VISIBLE);
//            } else {
//                mButtonReset.setVisibility(View.INVISIBLE);
//            }
        }
    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putLong("millisLeft", mTimeLeftInMillis);
//        outState.putBoolean("timerRunning", mTimerRunning);
//        outState.putLong("endTime", mEndTime);
//    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
//        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
//        updateCountDownText();
//        updateButtons();
//        if (mTimerRunning){
//            mEndTime = savedInstanceState.getLong("endTime");
//            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
//            startTimer();
//        }
//    }

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
        Intent intent = new Intent(TimeoutTimer.this, AlarmNotificationService.class);
        stopService(intent);
    }
}