package com.imastudio.firebase2019jan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFunction extends AppCompatActivity {
public Context c;
Animation animation;
    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c=MyFunction.this;
    }

    public void myanimation(EditText edtanimasi){
        animation= AnimationUtils.loadAnimation(c, R.anim.animasigetar);
        edtanimasi.setAnimation(animation);
    }

public void myIntent(Class kelastujuan){
    startActivity(new Intent(c,kelastujuan));
}


    public void myToast(String isipesan){
        Toast.makeText(c, isipesan, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog(String tittle) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle(tittle);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void keluar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(c);
        dialog.setTitle("infomasi");
        dialog.setMessage("Apakah anda yakin ingin keluar ? ");
        dialog.setPositiveButton("iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        dialog.setNegativeButton("tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    public String currentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
