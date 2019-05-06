package com.imastudio.implicitapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.imastudio.implicitapp.helper.MyFunction;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioRecorderActivity extends MyFunction {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.btnPlay)
    Button btnPlay;
    @BindView(R.id.btnRecordStop)
    Button btnRecordStop;
    private MediaRecorder recorder;
    private String lokasiFile;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recorder);
        ButterKnife.bind(this);
        reqPermission();
        btnPlay.setEnabled(false);
    }

    private void reqPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{
                                Manifest.permission.RECORD_AUDIO,

                        }, 100
                );
            }
            return;
        }if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,

                        }, 200
                );
            }
            return;
        }if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,

                        }, 300
                );
            }
            return;
        }
    }

    @OnClick({R.id.btnPlay, R.id.btnRecordStop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                mp = new MediaPlayer();
                try {
                    mp.setDataSource(lokasiFile);
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnRecordStop:
                if (btnRecordStop.getText().toString().equalsIgnoreCase("RECORD")){
                    recorder = new MediaRecorder();
                    lokasiFile = Environment.getExternalStorageDirectory().getAbsolutePath()
                            +"/REC"+currentDate()+".3gp";
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    recorder.setOutputFile(lokasiFile);
                    try {
                        recorder.prepare();
                        recorder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    btnRecordStop.setText("STOP");
                }else  if (btnRecordStop.getText().toString().equalsIgnoreCase("STOP")){
                   try {
                       recorder.stop();
                       recorder.release();

                   }catch (RuntimeException e){
                       e.printStackTrace();
                       Log.d("testingg",e.getLocalizedMessage() );
                   }
                    btnRecordStop.setText("RECORD");
                    btnPlay.setEnabled(true);
                }
                break;
        }
    }
}
