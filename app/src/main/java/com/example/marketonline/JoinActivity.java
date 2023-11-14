package com.example.marketonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.UUID;

public class JoinActivity extends AppCompatActivity {

    Button goLiveBtn;
    TextInputEditText nameInput,LiveId;
    String LiveID,name,userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        goLiveBtn = findViewById(R.id.go_live_btn) ;
        nameInput = findViewById(R.id.name_input) ;


        goLiveBtn.setOnClickListener((v)->{
            name = nameInput.getText().toString();
            if(name.isEmpty()){
                nameInput.setError("Nhap Name Cai Ban Ui");
                nameInput.requestFocus();
                return;
            }
            startMeeting();
        });
    }
    void startMeeting(){
        LiveID = getIntent().getStringExtra(  "Live_id");
        userID = UUID.randomUUID().toString();
        Intent intent = new Intent(JoinActivity.this, LiveActivity.class);
        intent.putExtra("user_id", userID);
        intent.putExtra("name", name);
        intent.putExtra("Live_id", LiveID);
        intent.putExtra("host", false);
        startActivity(intent);
    }
}