package com.imastudio.implicitapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmActivity extends AppCompatActivity {

    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.analogClock1)
    AnalogClock analogClock1;
    @BindView(R.id.startSetDialog)
    Button startSetDialog;
    @BindView(R.id.alarmprompt)
    TextView alarmprompt;
    private TimePickerDialog timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.startSetDialog)
    public void onViewClicked() {
        OpenAlarm();
    }

    private void OpenAlarm() {
        Calendar calendar = Calendar.getInstance();
        timePicker = new TimePickerDialog(this,ontimecustom,calendar.get(Calendar.HOUR_OF_DAY)
                ,calendar.get(Calendar.MINUTE),true);
        timePicker.setTitle("set alarm");
        timePicker.show();
    }
    TimePickerDialog.OnTimeSetListener ontimecustom  = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calnow = Calendar.getInstance();
            Calendar calset = (Calendar)calnow.clone();
            calset.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calset.set(Calendar.MINUTE,minute);
            calset.set(Calendar.SECOND,0);
            calset.set(Calendar.MILLISECOND,0);
            if (calset.compareTo(calnow)<=0){
                calset.add(Calendar.DATE,1);
            }else if (calset.compareTo(calnow)>0){
                Log.i("hasil",">0");
            }
            setAlarm(calset);
        }
    };

    private void setAlarm(Calendar calset) {
        alarmprompt.setText("alarm set on"+calset.getTime());
        Intent i = new Intent(this,AlarmReceiver.class);
        PendingIntent intent = PendingIntent.getBroadcast(this, 3, i, 0);
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, calset.getTimeInMillis(), intent);
    }
}
