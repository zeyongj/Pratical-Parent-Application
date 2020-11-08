package com.example.cmpt276project.model;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.cmpt276project.R;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmNotificationService extends Service {
    // Broadcast ID for Intent when notification is clicked
    private String BROADCAST_ID = "Broadcast ID to stop notification";
    private String ALARM_TIME_INT = "Countdown timer value for service";

    private long mTimeLeftInMillis;
    private Ringtone alarmRingtone;
    private Vibrator vibration;
    private CountDownTimer countDownTimer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Get the timer value from TimeoutTimer activity
        if (intent.getExtras()!=null) {
            mTimeLeftInMillis = intent.getLongExtra(ALARM_TIME_INT, 0);
        }

        // Register the BroadcastReceiver
        registerReceiver(notificationReceiver, new IntentFilter(BROADCAST_ID));

        // Set the timer if the timer isn't 0
        if (mTimeLeftInMillis!=0) {
            setTimer();
        }
        // Toast.makeText(getBaseContext(), String.format("ALARM TIMER %d", mTimeLeftInMillis), Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }

    public void showNotification() {
        // The codes are inspired and modified from the website: https://programmer.group/solutions-to-fail-to-post-notification-on-channel-null.html
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // Build notification with respect to API level of device
        if(Build.VERSION.SDK_INT >= 26)
        {
            buildNotificationNewSDK(manager);
        }
        else
        {
            buildNotificationOldSDK(manager);
        }
    }

    // Notification builder for API levels below 26
    private void buildNotificationOldSDK(NotificationManager manager) {
        // Create intent to stop alarm and vibration when notification is clicked
        Intent notificationIntent = new Intent(BROADCAST_ID);
        PendingIntent intent = PendingIntent.getBroadcast(getApplicationContext(), 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //When sdk version is less than26
        Notification notification = new NotificationCompat.Builder(getBaseContext())
                .setContentTitle("Alarm")
                .setContentText("The timeout timer has reached 0. Tap to stop.")
                .setSmallIcon(R.drawable.notification)
                // The icon is downloaded from the website: https://www.flaticon.com/free-icon/notification_1040216?term=notification&page=1&position=16
                .setContentIntent(intent)
                .setAutoCancel(true)
                .setColor(Color.RED)
                .build();
        manager.notify(1,notification);
    }

    // Notification builder for API levels above and including 26
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildNotificationNewSDK(NotificationManager manager) {
        //When sdk version is larger than26
        String id = "channel_1";
        String description = "143";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel(id, description, importance);
        channel.enableLights(true);
        channel.enableVibration(true);
        manager.createNotificationChannel(channel);

        // Create intent to stop alarm and vibration when notification is clicked
        Intent notificationIntent = new Intent(BROADCAST_ID);
        PendingIntent intent = PendingIntent.getBroadcast(getApplicationContext(), 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(getBaseContext(), id)
                .setCategory(Notification.CATEGORY_MESSAGE)
                // The icon is downloaded from the website: https://www.flaticon.com/free-icon/notification_1040216?term=notification&page=1&position=16
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Alarm")
                .setContentText("The timeout timer has reached 0. Tap to stop.")
                .setContentIntent(intent)
                .setAutoCancel(true)
                .setColor(Color.RED)
                .build();
        manager.notify(1, notification);
    }

    public void setTimer() {
        // Set timer with intervals every second
        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                // Show notification, play alarm, and vibrate when timer is up
                showNotification();
                alarm();
                vibrate();
            }
        }.start();
    }

    private void alarm() {
        long ringDelay = 6000;
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        alarmRingtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
        alarmRingtone.play();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                alarmRingtone.stop();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, ringDelay);
    }

    private void vibrate() {
        // Vibrate for 500 milliseconds
        vibration = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibration.vibrate(500);
    }

    private final BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Stop alarm and stop vibration
            alarmRingtone.stop();
            vibration.cancel();
            stopSelf();
        }
    };
}
