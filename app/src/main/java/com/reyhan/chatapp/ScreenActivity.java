package com.reyhan.chatapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ScreenActivity extends AppCompatActivity {

    //loading selama 4 detik
    private int loadingscreen = 4000;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        //cek apakah user sudah login atau belum
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseUser != null){
                    //jika ada user intent ke main activity
                    Intent intent = new Intent(ScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //intent ke login activity
                    Intent intent = new Intent(ScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },loadingscreen);

    }
}
