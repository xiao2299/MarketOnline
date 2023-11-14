package com.example.marketonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.example.library.API.APIClient;
import com.example.library.API.APIInterface;
import com.example.library.APIModel.GetLiveResponseAPIModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeLiveActivity extends AppCompatActivity {

    Button goNextLiveBtn;
    TextInputEditText nameInput,LiveId;
    String LiveID,name,userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_live);

        goNextLiveBtn = findViewById(R.id.go_live_btn) ;
        nameInput = findViewById(R.id.name_input) ;

        goNextLiveBtn.setOnClickListener((v)->{
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
        LiveID = getIntent().getStringExtra("Live_id");
        userID = UUID.randomUUID().toString();
        Intent intent = new Intent(HomeLiveActivity.this, NextLiveActivity.class);
        intent.putExtra("user_id", userID);
        intent.putExtra("name", name);
        intent.putExtra("host", false);
        startActivity(intent);
    }

    public void Xemlive(View view) {
        Intent intent = new Intent(HomeLiveActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void Live(View view) {
        Intent intent = new Intent(HomeLiveActivity.this, MainActivity.class);
        startActivity(intent);
    }
}