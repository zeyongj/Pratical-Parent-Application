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

        
    }
}