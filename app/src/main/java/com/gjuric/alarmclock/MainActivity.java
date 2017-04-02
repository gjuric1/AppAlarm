package com.gjuric.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    private TimePicker timePicker;

    private Button btSet;
    private Button btCancel;
    private TextView tvStatus;

    private PendingIntent pendingIntent;

    private AlarmReceiver alarm;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        timePicker = (TimePicker) findViewById(R.id.tpTimePicker);

        btSet = (Button) findViewById(R.id.btSet);
        btCancel = (Button) findViewById(R.id.btCancel);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        final Calendar calendar = Calendar.getInstance();

        //AlarmReceiver intent
        final Intent intent = new Intent(this, AlarmReceiver.class);



        btSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());

                final int hour = timePicker.getHour();
                final int minute = timePicker.getMinute();

                String hourString = String.valueOf(hour);
                String minuteString = String.valueOf(minute);

                if (minute < 10) {
                    minuteString = "0" + String.valueOf(minute);
                }

                setStatus("Alarm set for: " + hourString + ":" + minuteString);

                intent.putExtra("extra", "yes");

                //Pending intent
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //Set alarm manager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);

            }
        });


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setStatus("Alarm off");

                alarmManager.cancel(pendingIntent);

                intent.putExtra("extra", "no");

                sendBroadcast(intent);

            }
        });

    }

    private void setStatus(String status) {
        tvStatus.setText(status);
    }
}
