package com.imastudio.firebase2019jan.authentikasi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.imastudio.firebase2019jan.MainActivity;
import com.imastudio.firebase2019jan.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.logingoogle)
    Button logingoogle;
    @BindView(R.id.loginanonymous)
    Button loginanonymous;
    @BindView(R.id.loginphone)
    Button loginphone;
    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    if (user.isEmailVerified()){
                        Toast.makeText(LoginActivity.this, "Selamat anda berhasil login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }
                }else{
                    auth.signOut();
                }
            }
        };
    }

    @OnClick({R.id.btn_login, R.id.logingoogle, R.id.loginanonymous, R.id.loginphone, R.id.btn_reset_password, R.id.btn_signup})
    public void onViewClicked(View view) {
        String em = email.getText().toString();
        String pw = password.getText().toString();

        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(em)) {
                    email.setError("email harus diisi");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(pw)) {
                    password.setError("passowrd harus diisi");
                    password.requestFocus();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(em,pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);

                                if (user.isEmailVerified()){
                                    // Sign in success, update UI with the signed-in user's information
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this, "cek email untuk verifikasi", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "gagal authentikasi", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                break;
            case R.id.logingoogle:
                startActivity(new Intent(this,LoginGoogleActivity.class));

                break;
            case R.id.loginanonymous:
                break;
            case R.id.loginphone:
                startActivity(new Intent(this,LoginPhoneActivity.class));

                break;
            case R.id.btn_reset_password:
                break;
            case R.id.btn_signup:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (listener != null) {
            auth.removeAuthStateListener(listener);
        }
    }
}
