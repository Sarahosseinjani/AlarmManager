package com.sara.alarmmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnSetAlarm).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override public void onClick(View v) {
                setReminder();
            }
        });
        findViewById(R.id.btnCancelAlarm).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { cancelAlarm();
            }
        });
        findViewById(R.id.btnSelectTime).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { showTimePicker();
            }
        });
    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm is cancelled", Toast.LENGTH_SHORT).show();
    }
    private void showTimePicker() {
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE); String strHrsToShow = (calendar.get(Calendar.HOUR) == 0) ? "12" : calendar.get(Calendar.HOUR) + "";
        TimePickerDialog.OnTimeSetListener myTimeListener = (view, hourOfDay, minute1) -> {
            String am_pm = "";
            if (calendar.get(Calendar.AM_PM) == Calendar.AM) am_pm = "AM";
            else if (calendar.get(Calendar.AM_PM) == Calendar.PM) am_pm = "PM";
            if (view.isShown()) { calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute1);
            TextView tvSelectedTime = findViewById(R.id.tvSelectedTime);
            tvSelectedTime.setText(strHrsToShow + ":" + calendar.get(Calendar.MINUTE) + " " + am_pm);
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setReminder() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class); intent.putExtra("title", "This is Alarm Title");
        intent.putExtra("description", "This is Alarm Description");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }


}