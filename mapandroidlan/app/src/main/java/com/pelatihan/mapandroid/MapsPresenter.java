package com.pelatihan.mapandroid;

import com.pelatihan.mapandroid.helper.HeroHelper;
import com.pelatihan.mapandroid.model.Distance;
import com.pelatihan.mapandroid.model.Duration;
import com.pelatihan.mapandroid.model.LegsItem;
import com.pelatihan.mapandroid.model.ResponseMap;
import com.pelatihan.mapandroid.model.RoutesItem;
import com.pelatihan.mapandroid.network.MyRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsPresenter  implements MapsContract.MapPresenter{

    MapsContract.MapView view;
    private List<RoutesItem> dataMap;
    private List<LegsItem> legs;
    private Distance distance;
    private Duration duration;

    public MapsPresenter(MapsContract.MapView view) {
        this.view = view;
    }

    @Override
    public void getData(String lokasiawal, String lokasitujuan, String key) {
        MyRetrofit.getInstaceRetrofit().getRute(
                lokasiawal,
                lokasitujuan,
                key).enqueue(new Callback<ResponseMap>() {
            @Override
            public void onResponse(Call<ResponseMap> call, Response<ResponseMap> response) {
                if (response.isSuccessful()){
                    String status =response.body().getStatus();
                    if (status.equals("OK")){
                        dataMap = response.body().getRoutes();
                        legs = dataMap.get(0).getLegs();
                        distance = legs.get(0).getDistance();
                        duration = legs.get(0).getDuration();
                        view.dataJarak(distance.getText());
                        view.dataDurasi(duration.getText());
                        double harga = Math.ceil(Double.valueOf(distance.getValue()/1000)*1000);
                        view.dataHarga(HeroHelper.toRupiahFormat2(String.valueOf(harga)));
                        view.dataMap(dataMap);
                       view.pesan("berhasil menampilkan data");
                    }else{
                        view.pesan("gagal tampil data");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMap> call, Throwable t) {
            view.pesanError(t.getLocalizedMessage());
            }
        });
    }
}
