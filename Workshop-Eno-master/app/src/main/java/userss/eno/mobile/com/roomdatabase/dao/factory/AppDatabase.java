package userss.eno.mobile.com.roomdatabase.dao.factory;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import userss.eno.mobile.com.roomdatabase.dao.MahasiswaDAO;
import userss.eno.mobile.com.roomdatabase.model.Mahasiswa;

//TODO 1.3 Class ini digunakan untuk membuat objek dari Room Database itu sendiri,
// didalamnya terdapat class-class DAO yang akan digunakan

    @Database(entities = {Mahasiswa.class}, version = 4)
    public abstract class AppDatabase extends RoomDatabase {

        //Untuk mengakses Database menggunakan method abstract
        public abstract MahasiswaDAO mahasiswaDAO();
    }

