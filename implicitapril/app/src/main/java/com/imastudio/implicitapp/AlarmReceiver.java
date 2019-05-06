package com.imastudio.implicitapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer player = MediaPlayer.create(context, R.raw.alarm);
        player.start();
    }
}
