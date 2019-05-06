package com.imastudio.implicitapp;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WIFIActivity extends AppCompatActivity {

    @BindView(R.id.wifi)
    Switch wifi;
    private WifiManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        ButterKnife.bind(this);
        manager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);

        wifi.setChecked(status());
        wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wifiChangeStatus(isChecked);
            }
        });
    }

    private void wifiChangeStatus(boolean isChecked) {
        if (isChecked==true&& !manager.isWifiEnabled()){
            manager.setWifiEnabled(true);
            Toast.makeText(this, "Wifi Aktif", Toast.LENGTH_SHORT).show();
        }else if (isChecked==false&&manager.isWifiEnabled()){
            manager.setWifiEnabled(false);
            Toast.makeText(this, "wifi tidak aktif", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean status() {
        return manager.isWifiEnabled();
    }
}
