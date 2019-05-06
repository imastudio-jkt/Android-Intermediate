package com.pelatihan.mapandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pelatihan.mapandroid.helper.MyFunction;

public class MainActivity extends MyFunction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionGPS();
    }

    public void onPlace(View view) {
        startActivity(new Intent(this,PlacePickerActivity.class));

    }

    public void onMap(View view) {
        startActivity(new Intent(this,MapsActivity.class));
    }

    @Override
    public void onBackPressed() {

    }

}
