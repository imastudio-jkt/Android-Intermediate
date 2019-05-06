package com.imastudio.implicitapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AudioManagerActivity extends AppCompatActivity {

    private AudioManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_manager);
        manager = (AudioManager)getSystemService(AUDIO_SERVICE);
        permiss();
    }

    private void permiss() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }
    }

    public void onRing(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
                    manager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
                    manager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE,0);
                    manager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
                    manager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);
                } else {
                    manager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
                    manager.setStreamMute(AudioManager.STREAM_ALARM, false);
                    manager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    manager.setStreamMute(AudioManager.STREAM_RING, false);
                    manager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
                }
    }

    public void onVibrate(View view) {
        manager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    public void onSilent(View view) {
        manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
