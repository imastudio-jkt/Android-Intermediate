package com.imastudio.firebase2019jan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.imastudio.firebase2019jan.admob.AdmobActivity;
import com.imastudio.firebase2019jan.authentikasi.LoginActivity;
import com.imastudio.firebase2019jan.database.DatabaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.old_email)
    EditText oldEmail;
    @BindView(R.id.new_email)
    EditText newEmail;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.newPassword)
    EditText newPassword;
    @BindView(R.id.changeEmail)
    Button changeEmail;
    @BindView(R.id.changePass)
    Button changePass;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.remove)
    Button remove;
    @BindView(R.id.change_email_button)
    Button changeEmailButton;
    @BindView(R.id.change_password_button)
    Button changePasswordButton;
    @BindView(R.id.sending_pass_reset_button)
    Button sendingPassResetButton;
    @BindView(R.id.remove_user_button)
    Button removeUserButton;
    @BindView(R.id.sign_out)
    Button signOut;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       auth =FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        listener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user==null){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                }
            }
        };
    }

    @OnClick({R.id.changeEmail, R.id.changePass, R.id.send, R.id.change_email_button, R.id.change_password_button, R.id.sending_pass_reset_button, R.id.remove_user_button, R.id.sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.changeEmail:
                String nE =newEmail.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                if (user!=null&&!nE.equals("")){
                    user.updateEmail(nE).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "gagal ganti email"+task.getException(), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "berhasil gantil email", Toast.LENGTH_SHORT).show();
                                verifikasiemail();
                                FirebaseAuth.getInstance().signOut();
                               // finish();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else if (nE.equals("")){
                    newEmail.setError("email harus diisi");
                    progressBar.setVisibility(View.GONE);
                }
                break;
            case R.id.changePass:
                String nP =newPassword.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                if (user!=null&&!nP.equals("")) {
                        user.updatePassword(nP).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "gagal :" + task.getException(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "berhasil update/ganti password", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    auth.signOut();
                                    finish();

                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                else if (nP.equals("")){
                    newPassword.setError("password baru harus diisi");
                    progressBar.setVisibility(View.GONE);
                }

                break;
            case R.id.send:
                progressBar.setVisibility(View.VISIBLE);
                String om =oldEmail.getText().toString();
                if (user!=null&&!om.equals("")){
                    auth.sendPasswordResetEmail(om).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "gagal kirim reset email"+task.getException(), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "berhasil kirim reset email", Toast.LENGTH_SHORT).show();
                                auth.signOut();
                                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                                finish();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else if (om.equals("")){
                    oldEmail.setError("email lama harus diisi");
                    progressBar.setVisibility(View.GONE);
                }

                break;

            case R.id.change_email_button:
                changeEmail.setVisibility(View.VISIBLE);
                newEmail.setVisibility(View.VISIBLE);
                changePass.setVisibility(View.GONE);
                send.setVisibility(View.GONE);
                oldEmail.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);

                break;
            case R.id.change_password_button:
                changeEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                changePass.setVisibility(View.VISIBLE);
                send.setVisibility(View.GONE);
                oldEmail.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);
                break;
            case R.id.sending_pass_reset_button:
                changeEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                changePass.setVisibility(View.GONE);
                send.setVisibility(View.VISIBLE);
                oldEmail.setVisibility(View.VISIBLE);
                newPassword.setVisibility(View.GONE);

                break;
            case R.id.remove_user_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("informasi");
                builder.setMessage("apakah anda yakin ingin menghapus akun anda");
                builder.setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressBar.setVisibility(View.VISIBLE);
                        if (user!=null){
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "gagal hapus akun"+task.getException(), Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(MainActivity.this, "profile akun anda berhasil di hapus!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                                        finish();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }

                    }
                });

                builder.setNegativeButton("tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
            case R.id.sign_out:
                auth.signOut();

                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }

    private void verifikasiemail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
              //      FirebaseAuth.getInstance().signOut();

                    startActivity(new Intent(MainActivity.this,LoginActivity.class));

                    finish();
                } else {
                    //restart activity
//                    overridePendingTransition(0, 0);
//                    finish();
//                    overridePendingTransition(0, 0);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

    }


    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (listener!=null){
            auth.removeAuthStateListener(listener);
        }
    }

    public void onListhewan(View view) {
     //   myIntent(DatabaseFirebaseActivity.class);
startActivity(new Intent(this, DatabaseActivity.class));
    }

    public void onAdmob(View view) {
startActivity(new Intent(this, AdmobActivity.class));
   // AdmobActivity    //myIntent(AdmobActivity.class);
    }


}
