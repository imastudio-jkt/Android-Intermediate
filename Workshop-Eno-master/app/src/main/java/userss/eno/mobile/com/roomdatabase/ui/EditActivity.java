package userss.eno.mobile.com.roomdatabase.ui;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import userss.eno.mobile.com.roomdatabase.R;
import userss.eno.mobile.com.roomdatabase.dao.factory.AppDatabase;
import userss.eno.mobile.com.roomdatabase.model.Mahasiswa;

public class EditActivity extends AppCompatActivity {

    //TODO 1.9 disini kita akan memanggil method updateMahasiswa(), untuk mengedit data yang diubah.
    //Deklarasi Variable
    private TextInputEditText Nama, Jurusan, tanggalLahir;
    private AppDatabase database;
    private Button bSimpan;
    private RadioButton lakiLaki, perempuan;
    private RadioGroup jenisKelamin;
    private String myJenisKelamin;
    private Mahasiswa mahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Nama = findViewById(R.id.nama);
        Jurusan = findViewById(R.id.jurusan);
        tanggalLahir = findViewById(R.id.tanggal_lahir);
        jenisKelamin = findViewById(R.id.jenis_kelamin);
        lakiLaki = findViewById(R.id.laki_laki);
        perempuan = findViewById(R.id.perempuan);
        bSimpan = findViewById(R.id.save);

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dbMahasiswa").build();

        //Menampilkan data mahasiswa yang akan di edit
        getDataMahasiswa();

                    bSimpan.setOnClickListener(new View.OnClickListener() {
                                           @Override
            public void onClick(View v) {
                mahasiswa.setNama(Nama.getText().toString());
                mahasiswa.setJurusan(Jurusan.getText().toString());
                mahasiswa.setTanggalLahir(tanggalLahir.getText().toString());
                mahasiswa.setJenisKelamin(myJenisKelamin);
                updateData(mahasiswa);
            }
        });
    }

    //Method untuk menapilkan data mahasiswa yang akan di edit
    private void getDataMahasiswa(){
        //Mendapatkan data dari Intent
        mahasiswa = (Mahasiswa)getIntent().getSerializableExtra("data");

        Nama.setText(mahasiswa.getNama());
        Jurusan.setText(mahasiswa.getJurusan());
        tanggalLahir.setText(mahasiswa.getTanggalLahir());
        switch (mahasiswa.getJenisKelamin()){
            case "Laki-Laki":
                lakiLaki.setChecked(true);
                myJenisKelamin = "Laki-Laki";
                break;

            case "Perempuan":
                perempuan.setChecked(true);
                myJenisKelamin = "Perempuan";
                break;
        }
    }

    //Menjalankan method Update Data
    @SuppressLint("StaticFieldLeak")
    private void updateData(final Mahasiswa mahasiswa){
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                //Menjalankan proses insert data
                return database.mahasiswaDAO().updateMahasiswa(mahasiswa);
            }

            @Override
            protected void onPostExecute(Integer status) {
                //Menandakan bahwa data berhasil disimpan
                Toast.makeText(EditActivity.this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditActivity.this, ReadDataActivity.class));
                finish();
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditActivity.this, ReadDataActivity.class));
        finish();
    }
}


