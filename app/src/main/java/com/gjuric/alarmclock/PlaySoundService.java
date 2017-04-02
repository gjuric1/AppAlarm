package com.gjuric.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class PlaySoundService extends Service {

    MediaPlayer alarmSong;
    private int startId;
    private boolean isPlaying;
    private Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Alarm")
                .setContentText("Click here")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();


        //get extra values
        String state = intent.getExtras().getString("extra");



        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }

        if (!this.isPlaying && startId == 1) {
            alarmSong = MediaPlayer.create(this, R.raw.computer);
            alarmSong.start();

            notificationManager.notify(0, notification);

            this.isPlaying = true;
            this.startId = 0;


        }
        else if (!this.isPlaying && startId == 0) {

            this.isPlaying =false;
            this.startId = 0;
        }
        else if (!this.isPlaying && startId == 1) {
            this.isPlaying = true;
            this.startId = 0;
        }
        else {
            alarmSong.stop();
            alarmSong.reset();

            this.isPlaying = false;
            this.startId = 0;
        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.isPlaying = false;
    }
}
