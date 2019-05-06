package com.imastudio.firebase2019jan.database;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseClient {

    Context act;
    ListView listView;
    DatabaseReference reference;
    String url;
    Firebase firebase;
    ArrayList<ModelHewan> datahewan = new ArrayList<>();
    private CustomAdapter customAdapter;
    private ModelHewan h;

    public FirebaseClient(Context databaseActivity, ListView listview, DatabaseReference reference, String base_url) {

        act = databaseActivity;
        listView = listview;
        this.reference = reference;
        url = base_url;
        Firebase.setAndroidContext(act);

        firebase = new Firebase(url);
    }

    public FirebaseClient(Context act, DatabaseReference reference1) {
        this.reference = reference1;

        this.act = act;
    }


    public void insertDataHewan(String nama, String url, String info) {
        ModelHewan hewan = new ModelHewan();
        String id = reference.push().getKey();
        hewan.setId(id);
        hewan.setNama(nama);
        hewan.setInfo(info);
        hewan.setUrl(url);
        reference.child(id).setValue(hewan);

    }

    public void refreshData() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getUIUpdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getUIUpdates(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                getUIUpdates(dataSnapshot);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getUIUpdates(DataSnapshot dataSnapshot) {
        datahewan.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            h = new ModelHewan();

            h.setId(ds.getValue(ModelHewan.class).getId());
            h.setNama(ds.getValue(ModelHewan.class).getNama());
            h.setInfo(ds.getValue(ModelHewan.class).getInfo());
            h.setUrl(ds.getValue(ModelHewan.class).getUrl());
            datahewan.add(h);

        }
        if (datahewan.size() > 0) {
            customAdapter = new CustomAdapter(act, datahewan, reference, firebase);

            listView.setAdapter((ListAdapter) customAdapter);


        } else {
            Toast.makeText(act, "no data", Toast.LENGTH_SHORT).show();
        }
    }


    public void updatedata(String id, String nama, String url, String info) {
        ModelHewan hewan = new ModelHewan(id, nama, info, url);
        reference.child(id).setValue(hewan);
        Toast.makeText(act, "update data berhasil", Toast.LENGTH_SHORT).show();

    }

    public void deletedata(String id) {
        reference.child(id).removeValue();
        Toast.makeText(act, "data berhasil dihapus", Toast.LENGTH_SHORT).show();
//        datahewan.clear();
//        datahewan.add(h);
        customAdapter.notifyDataSetChanged();
    }
}
