package com.imastudio.implicitapp.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFunction extends AppCompatActivity {
public Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_my_function);

    c =MyFunction.this;
    }


    public void pesan(String isipesan){
        Toast.makeText(c, isipesan, Toast.LENGTH_SHORT).show();
    }
    public void pindahclass(Class kelastujuan){
        startActivity(new Intent(c, kelastujuan));
        finish();
    }

    public  String currentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
