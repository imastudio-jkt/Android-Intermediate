package userss.eno.mobile.com.roomdatabase.ui;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import userss.eno.mobile.com.roomdatabase.R;
import userss.eno.mobile.com.roomdatabase.dao.factory.AppDatabase;
import userss.eno.mobile.com.roomdatabase.model.Mahasiswa;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //TODO 1.4 disini kita masukan source code untuk input data kedalam Database menggunakan method insert
    // yang sudah kita buat tadi

            //Deklarasi Variable
            private TextInputEditText NIM, Nama, Jurusan, tanggalLahir;
            private AppDatabase database;
            private Button bSimpan, bLihatData;
            private RadioButton lakiLaki, perempuan;
            private String myJenisKelamin;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                NIM = findViewById(R.id.nim);
                Nama = findViewById(R.id.nama);
                Jurusan = findViewById(R.id.jurusan);
                tanggalLahir = findViewById(R.id.tanggal_lahir);
                lakiLaki = findViewById(R.id.laki_laki);
                perempuan = findViewById(R.id.perempuan);
                bSimpan = findViewById(R.id.save);
                bSimpan.setOnClickListener(this);
                bLihatData = findViewById(R.id.show);
                bLihatData.setOnClickListener(this);

                //Menentukan Jenis Kelamin pada Data Mahasiswa Baru
                if(lakiLaki.isChecked()){
                    myJenisKelamin = "Laki-Laki";
                }else if (perempuan.isChecked()){
                    myJenisKelamin = "Perempuan";
                }

                //Inisialisasi dan memanggil Room Database
                database = Room.databaseBuilder(
                        getApplicationContext(),
                        AppDatabase.class,
                        "dbMahasiswa") //Nama File Database yang akan disimpan
                        .build();
            }

            //Menjalankan method Insert Data
            @SuppressLint("StaticFieldLeak")
            private void insertData(final Mahasiswa mahasiswa){
                new AsyncTask<Void, Void, Long>() {
                    @Override
                    protected Long doInBackground(Void... voids) {
                        //Menjalankan proses insert data
                        return database.mahasiswaDAO().insertMahasiswa(mahasiswa);
                    }

                    @Override
                    protected void onPostExecute(Long status) {
                        //Menandakan bahwa data berhasil disimpan
                        Toast.makeText(MainActivity.this, "Status Row "+status, Toast.LENGTH_SHORT).show();
                    }
                }.execute();
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.save:

                        //Mengecek Data NIM dan Nama
                        if(NIM.getText().toString().isEmpty() || Nama.getText().toString().isEmpty()){
                            Toast.makeText(MainActivity.this, "NIM atau Nama TIdak Boleh Kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            //Membuat Instance/Objek Dari Class Entity Mahasisiwaa
                            Mahasiswa data = new Mahasiswa();

                            //Memasukan data yang diinputkan user pada database
                            data.setNim(NIM.getText().toString());
                            data.setNama(Nama.getText().toString());
                            data.setJurusan(Jurusan.getText().toString());
                            data.setTanggalLahir(tanggalLahir.getText().toString());
                            data.setJenisKelamin(myJenisKelamin);
                            insertData(data);

                            //Mengembalikan EditText menjadi seperti semula\
                            NIM.setText("");
                            Nama.setText("");
                            Jurusan.setText("");
                            tanggalLahir.setText("");
                        }
                        break;

                    case R.id.show:
                        startActivity(new Intent(MainActivity.this, ReadDataActivity.class));
                        break;
                }
            }
        }


