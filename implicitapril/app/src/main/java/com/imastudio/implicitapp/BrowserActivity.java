package com.imastudio.implicitapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
    }

    public void onAkseslink(View view) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com")));
    }
}
