package userss.eno.mobile.com.roomdatabase.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import userss.eno.mobile.com.roomdatabase.R;
import userss.eno.mobile.com.roomdatabase.model.Mahasiswa;

public class DetailDataActivity extends AppCompatActivity {
    //TODO 1.10
    private EditText getNIM, getName, getJurusan, getTanggalLahir, getJenisKelamin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);
        getNIM = findViewById(R.id.myNIM);
        getName = findViewById(R.id.myName);
        getJurusan = findViewById(R.id.myJurusan);
        getTanggalLahir = findViewById(R.id.myTanggalLahir);
        getJenisKelamin = findViewById(R.id.myJenisKelamin);

        getDetailData();
    }

    //Mendapatkan data yang akan ditampilkan secara detail
    private void getDetailData(){
        Mahasiswa mahasiswa = (Mahasiswa)getIntent().getSerializableExtra("detail");

        //Menampilkan data Mahasiswa pada activity
        if(mahasiswa != null){
            getNIM.setText(mahasiswa.getNim());
            getName.setText(mahasiswa.getNama());
            getJurusan.setText(mahasiswa.getJurusan());
            getTanggalLahir.setText(mahasiswa.getTanggalLahir());
            getJenisKelamin.setText(mahasiswa.getJenisKelamin());
        }
    }
}