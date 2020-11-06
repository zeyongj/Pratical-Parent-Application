package com.example.cmpt276project.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.cmpt276project.R;

public class AlarmNotificationService extends Service {
    private long mTimeLeftInMillis;
    private CountDownTimer mCountDownTimer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mTimeLeftInMillis = 10000;
        setTimer();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTimeLeftInMillis = 10000;
        setTimer();
        Toast.makeText(getBaseContext(), "asdfasdf", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    public void showNotification() {
        // The codes are inspired and modified from the website: https://programmer.group/solutions-to-fail-to-post-notification-on-channel-null.html
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= 26)
        {
            //When sdk version is larger than26
            String id = "channel_1";
            String description = "143";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(id, description, importance);
            channel.enableLights(true);
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(getBaseContext(), id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    // The icon is downloaded from the website: https://www.flaticon.com/free-icon/notification_1040216?term=notification&page=1&position=16
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle("Alarm")
                    .setContentText("The timeout timer has reached 0.")
                    .setAutoCancel(true)
                    .build();
            manager.notify(1, notification);
        }
        else
        {
            //When sdk version is less than26
            Notification notification = new NotificationCompat.Builder(getBaseContext())
                    .setContentTitle("Alarm")
                    .setContentText("The timeout timer has reached 0.")
                    .setSmallIcon(R.drawable.notification)
                    .build();
            manager.notify(1,notification);
        }
    }

    public void setTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) { //Every 1 second
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
            }

            @Override
            public void onFinish() {
                showNotification();
            }
        };

        mCountDownTimer.start();
    }
}
