package com.example.cmpt276project.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import com.example.cmpt276project.R;
@RequiresApi(api = Build.VERSION_CODES.O)
public class TimeoutTimer extends AppCompatActivity {
    // Initialize the variables
    String id ="channel_1";//id of channel
    String description = "123";//Description information of channel
    int importance = NotificationManager.IMPORTANCE_LOW;//The Importance of channel
    NotificationChannel channel = new NotificationChannel(id, "123", importance);//Generating channel
    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonSet1Min;
    private Button mButtonSet2Min;
    private Button mButtonSet3Min;
    private Button mButtonSet5Min;
    private Button mButtonSet10Min;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout_timer);

        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonSet = findViewById(R.id.button_set);
        mButtonSet1Min = findViewById(R.id.button_set1min);
        mButtonSet2Min = findViewById(R.id.button_set2min);
        mButtonSet3Min = findViewById(R.id.button_set3min);
        mButtonSet5Min = findViewById(R.id.button_set5min);
        mButtonSet10Min = findViewById(R.id.button_set10min);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

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
                }else{
                    startTimer();
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

    private void setCertainTime() {
        mButtonSet1Min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long millisInput = 1 * 60000;
                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });

        mButtonSet2Min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long millisInput = 2 * 60000;
                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });

        mButtonSet3Min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long millisInput = 3 * 60000;
                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });

        mButtonSet5Min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long millisInput = 5 * 60000;
                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });

        mButtonSet10Min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long millisInput = 10 * 60000;
                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });
    }
}