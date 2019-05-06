package com.pelatihan.mapandroid;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pelatihan.mapandroid.helper.DirectionMapsV2;
import com.pelatihan.mapandroid.helper.GPStrack;
import com.pelatihan.mapandroid.helper.MyFunction;
import com.pelatihan.mapandroid.model.Distance;
import com.pelatihan.mapandroid.model.Duration;
import com.pelatihan.mapandroid.model.LegsItem;
import com.pelatihan.mapandroid.model.RoutesItem;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pelatihan.mapandroid.helper.MyConstant.REQ_AKHIR;
import static com.pelatihan.mapandroid.helper.MyConstant.REQ_AWAL;

public class MapsActivity extends MyFunction implements OnMapReadyCallback, MapsContract.MapView {

    private static final int REQUEST_LOCATION = 2;
    @BindView(R.id.edtawal)
    EditText edtawal;
    @BindView(R.id.edtakhir)
    EditText edtakhir;
    @BindView(R.id.textjarak)
    TextView textjarak;
    @BindView(R.id.textwaktu)
    TextView textwaktu;
    @BindView(R.id.textharga)
    TextView textharga;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.btnlokasiku)
    Button btnlokasiku;
    @BindView(R.id.btnpanorama)
    Button btnpanorama;
    @BindView(R.id.linearbottom)
    LinearLayout linearbottom;
    @BindView(R.id.spinmode)
    Spinner spinmode;
    @BindView(R.id.relativemap)
    RelativeLayout relativemap;
    @BindView(R.id.frame1)
    FrameLayout frame1;
    MapsPresenter presenter;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private GPStrack gps;
    private double lat;
    private double lon;
    private String name_location;
    private LatLng lokasiku;
    private Intent intent;
    private List<RoutesItem> dataMap;
    private List<LegsItem> legs;
    private Distance distance;
    private Duration duration;
    private String dataGaris;
    private double latawal;
    private double lonawal;
    private double latakhir;
    private double lonakhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        cekStatusGPS();
        presenter = new MapsPresenter(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        myLocation();
    }

    private void myLocation() {
        gps = new GPStrack(this);
        if (gps.canGetLocation() && mMap != null) {
            lat = gps.getLatitude();
            lon = gps.getLongitude();
            name_location = convertLocation(lat, lon);
            Toast.makeText(this, "lat :" + lat + "\n lon :" + lon, Toast.LENGTH_SHORT).show();
            lokasiku = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(lokasiku).title(name_location)).
                    setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiku, 17));
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    private String convertLocation(double lat, double lon) {
        name_location = null;
        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        try {
            List<Address> list = geocoder.getFromLocation(lat, lon, 1);
            if (list != null && list.size() > 0) {
                name_location = list.get(0).getAddressLine(0) + "" + list.get(0).getCountryName();

                //fetch data from addresses
            } else {
                Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show();
                //display Toast message
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name_location;
    }

    @OnClick({R.id.edtawal, R.id.edtakhir, R.id.btnlokasiku, R.id.btnpanorama})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edtawal:
                searchLocation(REQ_AWAL);
                break;
            case R.id.edtakhir:
                searchLocation(REQ_AKHIR);
                break;
            case R.id.btnlokasiku:
                myLocation();
                break;
            case R.id.btnpanorama:
                myPanorama();
                break;
        }
    }

    private void myPanorama() {
        relativemap.setVisibility(View.GONE);
        frame1.setVisibility(View.VISIBLE);
        SupportStreetViewPanoramaFragment panorama = (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                .findFragmentById(R.id.panorama);
        panorama.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
                streetViewPanorama.setPosition(lokasiku);
            }
        });
    }

    private void searchLocation(int reqcode) {
        AutocompleteFilter filter = new AutocompleteFilter.Builder().
                setCountry("ID")
                .build();

        try {
            intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(filter)
                    .build(MapsActivity.this);
            startActivityForResult(intent, reqcode);
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Place place = PlaceAutocomplete.getPlace(this, data);
        if (requestCode == REQ_AWAL && resultCode == RESULT_OK) {
            latawal = place.getLatLng().latitude;
            lonawal = place.getLatLng().longitude;
            name_location = place.getName().toString();
            edtawal.setText(name_location);
            mMap.clear();
            addMarker(latawal, lonawal);
        } else if (requestCode == REQ_AKHIR && resultCode == RESULT_OK) {
            latakhir = place.getLatLng().latitude;
            lonakhir = place.getLatLng().longitude;
            name_location = place.getName().toString();
            edtakhir.setText(name_location);
            // mMap.clear();
            addMarker(latakhir, lonakhir);
            String key = getString(R.string.google_maps_key);
            String lokasiawal = String.valueOf(latawal + "," + lonawal);
            String lokasiakhir = String.valueOf(latakhir + "," + lonakhir);
            presenter.getData(lokasiawal, lokasiakhir, key);
        }
    }


    private void addMarker(double lat, double lon) {
        lokasiku = new LatLng(lat, lon);
        name_location = convertLocation(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiku, 15));
        mMap.addMarker(new MarkerOptions().position(lokasiku).title(name_location));

    }

    @Override
    public void pesan(String isipesan) {
        Toast.makeText(this, isipesan, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dataJarak(String jarak) {
        textjarak.setText(jarak);
    }

    @Override
    public void dataDurasi(String durasi) {
        textwaktu.setText(durasi);
    }

    @Override
    public void dataHarga(String harga) {
        textharga.setText(harga);
    }

    @Override
    public void dataMap(List<RoutesItem> dataMap) {
        DirectionMapsV2 mapsV2 = new DirectionMapsV2(MapsActivity.this);
        dataGaris = dataMap.get(0).getOverviewPolyline().getPoints();
        mapsV2.gambarRoute(mMap, dataGaris);
    }

    @Override
    public void pesanError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
