package com.imastudio.firebase2019jan.admob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.MobileAds;
import com.imastudio.firebase2019jan.R;

public class AdmobActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admob);
        MobileAds.initialize(this, "ca-app-pub-7615796948097955~8359887971");
    }

    public void onbanner(View view) {
        startActivity(new Intent(this,BannerActivity.class));
    }

    public void oninterstitial(View view) {
        startActivity(new Intent(this,InterstitialActivity.class));

    }

    public void onreward(View view) {
        startActivity(new Intent(this,RewardVideoActivity.class));

    }
}
