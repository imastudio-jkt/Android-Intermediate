package com.imastudio.implicitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmailActivity extends AppCompatActivity {

    @BindView(R.id.edtto)
    EditText edtto;
    @BindView(R.id.edtsubject)
    EditText edtsubject;
    @BindView(R.id.edtmessage)
    EditText edtmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        ButterKnife.bind(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i==R.id.mn_send){
        String to = edtto.getText().toString();
        String msg = edtmessage.getText().toString();
        String sub = edtsubject.getText().toString();
            if (TextUtils.isEmpty(to)){
                edtto.setError("tujuan tidak boleh kosong");
                edtto.requestFocus();
            }else if (TextUtils.isEmpty(msg)){
                edtmessage.setError("message tidak boleh kosong");
                edtmessage.requestFocus();

            }else if (TextUtils.isEmpty(sub)){
                edtsubject.setError("subject tidak boleh kosong");
                edtsubject.requestFocus();
            }else{
                Intent  intent  = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{to} );
                intent.putExtra(Intent.EXTRA_TEXT,msg );
                intent.putExtra(Intent.EXTRA_SUBJECT,   sub );
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Email Client"));
            }
        }else{
        edtto.setText("");
        edtsubject.setText("");
        edtmessage.setText("");
        }
        return super.onOptionsItemSelected(item);
    }
}
