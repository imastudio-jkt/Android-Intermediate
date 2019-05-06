package com.imastudio.implicitapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TTSActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.btnSpeech)
    Button btnSpeech;
    @BindView(R.id.activity_tts)
    RelativeLayout activityTts;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        ButterKnife.bind(this);
        tts = new TextToSpeech(this,this);
        btnSpeech.setEnabled(false);
    }

    @OnClick(R.id.btnSpeech)
    public void onViewClicked() {
        String text = editText.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    public void onInit(int status) {
    if (status==TextToSpeech.SUCCESS){
        Locale bahasa = new Locale("id","ID");
        int bahasa2 =tts.setLanguage(bahasa);
        if (bahasa2==TextToSpeech.LANG_MISSING_DATA||bahasa2==TextToSpeech.LANG_NOT_SUPPORTED){
            Toast.makeText(this, "bahasa tidak mendukung", Toast.LENGTH_SHORT).show();
            Log.e("TTS", "This Language is not supported");
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        }else{
            onViewClicked();
            btnSpeech.setEnabled(true);
        }
    }
    }
}
