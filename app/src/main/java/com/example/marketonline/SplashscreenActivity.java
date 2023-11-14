package com.example.marketonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;

public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        //Paper.init(this);
        Thread thread=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(1500);
                }catch (Exception ex){
                }finally {
                    Intent intent = new Intent(SplashscreenActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
}