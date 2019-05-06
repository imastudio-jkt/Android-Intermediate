package com.pelatihan.mapandroid;

import com.pelatihan.mapandroid.model.RoutesItem;

import java.util.List;

public interface MapsContract {

    interface MapView{
        void pesan(String isipesan);
        void dataJarak(String jarak);
        void dataDurasi(String durasi);
        void dataHarga(String harga);
        void dataMap(List<RoutesItem> dataMap);
        void pesanError(String msg);
    }

    interface MapPresenter{
        void getData(String lokasiawal,String lokasitujuan,String key);
    }
}
