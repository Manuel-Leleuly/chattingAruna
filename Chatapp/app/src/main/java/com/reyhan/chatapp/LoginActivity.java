package com.reyhan.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Button loginbutton;
    private TextView regisbutton;
    private TextView forgotpass;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //panggil fungsi firebase
        auth = FirebaseAuth.getInstance();

        //binding
        email = findViewById(R.id.emailET);
        password = findViewById(R.id.passwordET);
        regisbutton = findViewById(R.id.registerTextView);
        loginbutton = findViewById(R.id.loginBtn);
        forgotpass = findViewById(R.id.forgot_password);

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                //login dengan email dan passoword beserta error handling
                if (TextUtils.isEmpty(mail)){
                    email.setError("Email tidak boleh kosong");
                } else if (TextUtils.isEmpty(pass)){
                    password.setError("Password tidak boleh kosong");
                } else {
                    auth.signInWithEmailAndPassword(mail,pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent regisintent = new Intent(LoginActivity.this, MainActivity.class);
                                        regisintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(regisintent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Email atau Password salah", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
        regisbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regisintent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regisintent);
            }
        });
    }
}
