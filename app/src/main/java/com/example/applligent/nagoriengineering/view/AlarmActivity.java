package com.example.applligent.nagoriengineering.view;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextClock;
import android.widget.TimePicker;

import com.example.applligent.nagoriengineering.R;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmActivity extends AppCompatActivity {
    TimePicker alarmTime;
    TextClock currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarmTime = findViewById(R.id.timePicker);

        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        currentTime = findViewById(R.id.textClock);
        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentTime.getText().toString().equals(AlarmTime())) {
                    r.play();

                } else {
                    r.stop();
                }

            }
        }, 0, 1000);
    }

    public String AlarmTime() {
        Integer alarmHour = alarmTime.getCurrentHour();
        Integer alarmMinute = alarmTime.getCurrentMinute();
        String stringAlarmMinutes;
        if (alarmMinute < 10) {
            stringAlarmMinutes = "0";
            stringAlarmMinutes = stringAlarmMinutes.concat(alarmMinute.toString());

        } else {
            stringAlarmMinutes = alarmMinute.toString();
        }

        String stringAlarmTime;

        if (alarmHour > 12) {
            alarmHour = alarmHour - 12;
            stringAlarmTime = alarmHour.toString().concat(":").concat(stringAlarmMinutes).concat(" PM");
        } else {
            stringAlarmTime = alarmHour.toString().concat(":").concat(stringAlarmMinutes).concat(":").concat(" AM");
        }
        return stringAlarmTime;
    }
}