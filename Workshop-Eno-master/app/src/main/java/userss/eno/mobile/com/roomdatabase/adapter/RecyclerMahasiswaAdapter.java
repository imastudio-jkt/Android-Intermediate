package userss.eno.mobile.com.roomdatabase.adapter;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import userss.eno.mobile.com.roomdatabase.R;
import userss.eno.mobile.com.roomdatabase.dao.factory.AppDatabase;
import userss.eno.mobile.com.roomdatabase.model.Mahasiswa;
import userss.eno.mobile.com.roomdatabase.ui.DetailDataActivity;
import userss.eno.mobile.com.roomdatabase.ui.EditActivity;

//TODO 1.6 pada class ini kita mengatur bagaimana data akan tampil didalam RecyclerView, seperti Inisialisasi ArrayList,
// menentuan view yang akan digunakan dan mengambil data yang akan ditampilkan.

public class RecyclerMahasiswaAdapter extends RecyclerView.Adapter<RecyclerMahasiswaAdapter.ViewHolder> {

    //Deklarasi Variable
    private ArrayList<Mahasiswa> daftarMahasiswa;
    private AppDatabase appDatabase;
    private Context context;

    public RecyclerMahasiswaAdapter(ArrayList<Mahasiswa> daftarMahasiswa, Context context) {

        //Inisialisasi data yang akan digunakan
        this.daftarMahasiswa = daftarMahasiswa;
        this.context = context;
        appDatabase = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class, "dbMahasiswa").allowMainThreadQueries().build();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //Deklarasi View yang akan digunakan
        private TextView Nim, Nama;
        private CardView item;

        ViewHolder(View itemView) {
            super(itemView);
            Nim = itemView.findViewById(R.id.nim);
            Nama = itemView.findViewById(R.id.nama);
            item = itemView.findViewById(R.id.cvMain);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inisialisasi Layout Item untuk RecyclerView
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        //Deklarasi Variable untuk mendapatkan data dari Database melalui Array
        String getNIM = daftarMahasiswa.get(position).getNim();
        String getNama = daftarMahasiswa.get(position).getNama();

        //Menampilkan data berdasarkan posisi Item dari RecyclerView
        holder.Nim.setText(getNIM);
        holder.Nama.setText(getNama);

        //TODO 1.8 tambahkan method baru bernama onEditData serta tambahkan onLongClickListener pada widget CardView,
        // sehingga saat user menekan salah satu item maka akan muncul menu pilihan yatu Edit dan Delete.
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mahasiswa mahasiswa = appDatabase.mahasiswaDAO()
                        .selectDetailMahasiswa(daftarMahasiswa.get(position).getNim());
                context.startActivity(new Intent(context, DetailDataActivity.class).putExtra("detail", mahasiswa));
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CharSequence[] menuPilihan = {"Edit", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext())
                        .setTitle("Pilih Aksi")
                        .setItems(menuPilihan, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        /*
                                         Menjalankan Perintah Edit Data
                                         Menggunakan Bundle untuk mengambil data yang akan Diedit
                                         */
                                        onEditData(position, context);
                                        break;

                                    case 1:
                                        onDeleteData(position);
                                        break;
                                }
                            }
                        });
                dialog.create();
                dialog.show();
                return true;
            }
        });
    }
    //TODO 1.9 isini kita akan membuat method baru bernama onDeleteData(),
    // method ini akan dijalankan pada fungsi onLongClickListener().

    //Menghapus Data dari Room Database yang dipilih oleh user
    private void onDeleteData(int position){
        appDatabase.mahasiswaDAO().deleteMahasiswa(daftarMahasiswa.get(position));
        daftarMahasiswa.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, daftarMahasiswa.size());
        Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
    }

    //Mengirim Data yang akan diedit dari ArrayList berdasarkan posisi item pada RecyclerView
    private void onEditData(int position, Context context){
        context.startActivity(new Intent(context, EditActivity.class).putExtra("data", daftarMahasiswa.get(position)));
        ((Activity)context).finish();
    }
    @Override
    public int getItemCount() {
        //Menghitung data / ukuran dari Array
        return daftarMahasiswa.size();
    }
}