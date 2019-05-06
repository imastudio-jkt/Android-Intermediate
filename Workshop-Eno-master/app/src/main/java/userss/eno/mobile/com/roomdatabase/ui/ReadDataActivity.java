package userss.eno.mobile.com.roomdatabase.ui;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import userss.eno.mobile.com.roomdatabase.R;
import userss.eno.mobile.com.roomdatabase.adapter.RecyclerMahasiswaAdapter;
import userss.eno.mobile.com.roomdatabase.dao.factory.AppDatabase;
import userss.eno.mobile.com.roomdatabase.model.Mahasiswa;

class ReadDataActivity extends AppCompatActivity {

    //TODO 1.7 disini kita akan memsang atribut-artibut yang diperlukan RecyclerView seperti adapter
    // dan Data Array dari Database.

    //Deklarasi Variable
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase database;
    private ArrayList<Mahasiswa> daftarMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);
        getSupportActionBar().setTitle("Daftar Mahasiswa");

        //Inisialisasi ArrayList
        daftarMahasiswa = new ArrayList<>();

        //Inisialisasi RoomDatabase
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dbMahasiswa").allowMainThreadQueries().build();

        /*
         * Mengambil data Mahasiswa dari Database
         * lalu memasukannya ke kedalam ArrayList (daftarMahasiswa)
         */
        daftarMahasiswa.addAll(Arrays.asList(database.mahasiswaDAO().readDataMahasiswa()));

        //Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.dataItem);

        //Agar ukuran RecyclerView tidak berubah
        recyclerView.setHasFixedSize(true);

        //Menentukan bagaimana item pada RecyclerView akan tampil
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Mamasang adapter pada RecyclerView
        adapter = new RecyclerMahasiswaAdapter(daftarMahasiswa, ReadDataActivity.this);
        recyclerView.setAdapter(adapter);
    }
}

