package com.imastudio.implicitapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmsActivity extends AppCompatActivity {

    private static final int REQTELF = 2;
    @BindView(R.id.edt)
    EditText edt;
    @BindView(R.id.edtmessage)
    EditText edtmessage;
    @BindView(R.id.btnsmsintent)
    Button btnsmsintent;
    @BindView(R.id.btnkirimsms)
    Button btnkirimsms;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.edt, R.id.btnsmsintent, R.id.btnkirimsms})
    public void onViewClicked(View view) {
        String to =edt.getText().toString();
        String msg =edtmessage.getText().toString();
        switch (view.getId()) {
            case R.id.edt:
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(i,REQTELF);

                break;
            case R.id.btnsmsintent:
                if (TextUtils.isEmpty(to)){
                    edt.setError("tujuan tidak boleh kosong");
                    edt.requestFocus();
                }else if (TextUtils.isEmpty(msg)){
                    edtmessage.setError("message tidak boleh kosong");
                    edtmessage.requestFocus();

                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setType("vnd.android-dir/mms-sms");
                    intent.putExtra("address",to );
                    intent.putExtra("sms_body", msg);
                    startActivity(intent);
                }
                break;
            case R.id.btnkirimsms:
            try{
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(to, null, msg, null, null);
                Toast.makeText(this, "terkirim", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "tidak terkirim", Toast.LENGTH_SHORT).show();

            }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQTELF&&resultCode==RESULT_OK){
            Cursor cursor = null;
            try {
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                if (cursor != null && cursor.moveToNext()) {
                    phone = cursor.getString(0);
                    edt.setText(phone);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
