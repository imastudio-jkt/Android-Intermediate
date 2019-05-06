package userss.eno.mobile.com.roomdatabase.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

//TODO 1.1 kita akan membuat class Entity nya terlebih dahulu, pada class ini kita
// akan mendefinisikan atribut-atribut yang diperlukan untuk table Mahasiswa

@Entity(tableName = "tMahasiswa") //Membuat tabel baru dengan nama "mahasiswa"

public class Mahasiswa implements Serializable {

        //Membuat kolom NIM sebagai Primary Key
        @NonNull
        @PrimaryKey
        @ColumnInfo(name = "nim_mahasiswa")
        private
        String nim;

        //Membuat kolom Nama
        @ColumnInfo(name = "nama_mahasiswa")
        private
        String nama;

        //Membuat kolom Tanggal Lahir
        @ColumnInfo(name = "tanggal_lahir")
        private
        String tanggalLahir;

        //Membuat kolom Jurusan
        @ColumnInfo(name = "jurusan")
        private
        String jurusan;

        //Jenis Kelamin
        @ColumnInfo(name = "jenis_kelamin")
        private
        String jenisKelamin;

        //Method untuk mengambil data NIM
        @NonNull
        public String getNim() {
            return nim;
        }

        //Method untuk memasukan data NIM
        public void setNim(@NonNull String nim) {
            this.nim = nim;
        }

        //Method untuk mengambil data Nama
        public String getNama() {
            return nama;
        }

        //Method untuk memasukan data Nama
        public void setNama(String nama) {
            this.nama = nama;
        }

        //Method untuk mengambil data Tanggal Lahir
        public String getTanggalLahir() {
            return tanggalLahir;
        }

        //Method untuk memasukan data Tanggal Lahir
        public void setTanggalLahir(String tanggalLahir) {
            this.tanggalLahir = tanggalLahir;
        }

        //Method untuk mengambil data Jurusan
        public String getJurusan() {
            return jurusan;
        }

        //Method untuk memasukan data Jurusan
        public void setJurusan(String jurusan) {
            this.jurusan = jurusan;
        }

        //Method untuk mengambil data Jenis Kelamin
        public String getJenisKelamin() {
            return jenisKelamin;
        }

        //Method untuk memasukan data Jenis Kelamin
        public void setJenisKelamin(String jenisKelamin) {
            this.jenisKelamin = jenisKelamin;
        }
    }

