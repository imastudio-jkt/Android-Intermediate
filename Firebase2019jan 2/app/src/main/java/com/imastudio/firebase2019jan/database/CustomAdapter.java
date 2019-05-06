package com.imastudio.firebase2019jan.database;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.imastudio.firebase2019jan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CustomAdapter extends BaseAdapter {

    Context activity;
    ArrayList<ModelHewan> datahewan;
    DatabaseReference reference;
    Firebase firebase;
    private FirebaseClient client;
    private String id;

    public CustomAdapter(Context act, ArrayList<ModelHewan> datahewan, DatabaseReference reference, Firebase firebase) {
        activity =act;
        this.datahewan=datahewan;
        this.reference=reference;
        this.firebase=firebase;
    }

    @Override
    public int getCount() {
        return datahewan.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_layout,null);
        client = new FirebaseClient(activity,reference);
        MyHolder holder = new MyHolder(convertView);
        holder.nama.setText(datahewan.get(position).getNama());
        holder.info.setText(datahewan.get(position).getInfo());
        PicassoClient picassoClient = new PicassoClient();
        picassoClient.tampilgambar(activity,datahewan.get(position).getUrl(),holder.imghewan);
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDeleteData(position);
            }
        });
        return convertView;
    }

    private void updateDeleteData(int i) {
        final Dialog dialog = new Dialog(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.updatedelete_layout, null);
        dialog.setContentView(view);
        final EditText nameEditext = (EditText) dialog.findViewById(R.id.nameEditText);
        final EditText urlEditext = (EditText) dialog.findViewById(R.id.urlEditText);
        final EditText infoEditext = (EditText) dialog.findViewById(R.id.infoEditText);
        Button btnupdate = (Button) dialog.findViewById(R.id.updateBtn);
        Button btndelete = (Button) dialog.findViewById(R.id.deleteBtn);
        final String name = datahewan.get(i).getNama();
        final String info = datahewan.get(i).getInfo();
        final String url = datahewan.get(i).getUrl();

        id = datahewan.get(i).getId();
        nameEditext.setText(name);
        urlEditext.setText(url);
        infoEditext.setText(info);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.updatedata(id
                        ,nameEditext.getText().toString()
                        ,urlEditext.getText().toString()
                        ,infoEditext.getText().toString());
                dialog.dismiss();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.deletedata(id);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private class MyHolder {
        TextView nama,info;
        ImageView imghewan;

        public MyHolder(View v) {
            nama =v.findViewById(R.id.nameTxt);
            info =v.findViewById(R.id.infoTxt);
            imghewan =v.findViewById(R.id.beachimage);
        }
    }

    private class PicassoClient {
        public void tampilgambar(Context a,String url,ImageView img){
            if (url!=null&&url.length()>0){
                Picasso.with(a).load(url).placeholder(R.drawable.noimage).into(img);
            }else{
                Picasso.with(a).load(R.drawable.logofirebase);

            }
        }
    }
}
