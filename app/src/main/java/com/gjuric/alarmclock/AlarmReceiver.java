package com.gjuric.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String state = intent.getExtras().getString("extra");

        //Intent to PlaySoundService
        Intent playIntent = new Intent(context, PlaySoundService.class);

        playIntent.putExtra("extra", state);

        context.startService(playIntent);

    }
}
