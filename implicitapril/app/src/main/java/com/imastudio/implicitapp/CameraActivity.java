package com.imastudio.implicitapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.imastudio.implicitapp.helper.MyFunction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends MyFunction {

    @BindView(R.id.btncamera)
    Button btncamera;
    @BindView(R.id.btnshow1)
    Button btnshow1;
    @BindView(R.id.imgshow)
    ImageView imgshow;
    private Uri lokasifile;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
              StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        ButterKnife.bind(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{
                                Manifest.permission.CAMERA,

                        }, 100
                );
            }
            return;
        }
    }

    @OnClick({R.id.btncamera, R.id.btnshow1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btncamera:
                String folder = "gambarsaya";
                File f = new File(Environment.getExternalStorageDirectory(), folder);
                if (!f.exists()) {
                    f.mkdir();
                }
                File filegambar = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/" + folder + "/PIC" + currentDate() + ".jpg");
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                lokasifile = Uri.fromFile(filegambar);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.putExtra(MediaStore.EXTRA_OUTPUT, lokasifile);
                startActivityForResult(i, 2);
                break;
            case R.id.btnshow1:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 4);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            pesan("berhasil mengambil gambar ,lokasi file :" + lokasifile.toString());
        } else if (requestCode == 4 && resultCode == RESULT_OK) {
            Uri lokasigambar = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(lokasigambar);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap = BitmapFactory.decodeStream(inputStream);
            imgshow.setImageBitmap(bitmap);
        }
    }
}

