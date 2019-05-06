package com.imastudio.implicitapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.imastudio.implicitapp.helper.MyFunction;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VIDEOActivity extends MyFunction {

    @BindView(R.id.btnvideo)
    Button btnvideo;
    private Uri lokasifile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnvideo)
    public void onViewClicked() {
        String folder = "videosaya";
        File f = new File(Environment.getExternalStorageDirectory(), folder);
        if (!f.exists()) {
            f.mkdir();
        }
        File filegambar = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + folder + "/VID" + currentDate() + ".mp4");
        Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        lokasifile = Uri.fromFile(filegambar);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.putExtra(MediaStore.EXTRA_OUTPUT, lokasifile);
        startActivityForResult(i, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            pesan("berhasil mengambil video ,lokasi file :" + lokasifile.toString());
        }
    }
}
