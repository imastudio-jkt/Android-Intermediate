package userss.eno.mobile.com.roomdatabase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import userss.eno.mobile.com.roomdatabase.model.Mahasiswa;

//TODO 1.2 interface ini berisi method-method khusus yang digunakan untuk mengakses database

    @Dao
    public interface MahasiswaDAO {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        long insertMahasiswa(Mahasiswa mahasiswa);

        @Query("SELECT * FROM tMahasiswa")
        Mahasiswa[] readDataMahasiswa();

        @Update
        int updateMahasiswa(Mahasiswa mahasiswa);

        @Delete
        void deleteMahasiswa(Mahasiswa mahasiswa);

        @Query("SELECT * FROM tMahasiswa WHERE nim_mahasiswa = :nim LIMIT 1")
        Mahasiswa selectDetailMahasiswa(String nim);
    }

