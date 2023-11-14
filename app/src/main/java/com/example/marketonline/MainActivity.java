package com.example.marketonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.library.API.APIClient;
import com.example.library.API.APIInterface;
import com.example.library.APIModel.GetLiveResponseAPIModel;
import com.example.library.APIModel.InsertLiveResponseAPIModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


//    TextInputEditText LiveIdInput;
    TextInputEditText nameInput, nameInput1;
    String LiveID,name,userID;
    APIInterface apiInterface;
    ListView listviewBasic;
    ArrayAdapter<String> itemsAdapter;
    List<String> items = new ArrayList<>();
    Button goLiveBtn, goNextLive, btnHome, btnHome2, btnHome3, btnLive, btnLive2, btnLive3, btnDao, btnDao2, btnDao3;


    RelativeLayout Trang1, Trang2, Trang3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goLiveBtn = findViewById(R.id.go_live_btn) ;
        goNextLive = findViewById(R.id.go_live_btn1) ;
//        LiveIdInput = findViewById(R.id.live_id_input);
        nameInput = findViewById(R.id.name_input) ;
        nameInput1 = findViewById(R.id.name_input1);
        btnHome = findViewById(R.id.btnHome);
        btnHome2 = findViewById(R.id.btnHome2);
        btnHome3 = findViewById(R.id.btnHome3);
        btnDao = findViewById(R.id.btnDao);
        btnDao2 = findViewById(R.id.btnDao2);
        btnDao3 = findViewById(R.id.btnDao3);
        btnLive = findViewById(R.id.btnLive);
        btnLive2 = findViewById(R.id.btnLive2);
        btnLive3 = findViewById(R.id.btnLive3);
        Trang1 = findViewById(R.id.Trang1);
        Trang2 = findViewById(R.id.Trang2);
        Trang3 = findViewById(R.id.Trang3);
        Trang2.setVisibility(View.INVISIBLE);
        Trang3.setVisibility(View.INVISIBLE);
        btnHome.setOnClickListener(l);
        btnHome2.setOnClickListener(l);
        btnHome3.setOnClickListener(l);
        btnLive.setOnClickListener(l);
        btnLive2.setOnClickListener(l);
        btnLive3.setOnClickListener(l);
        btnDao.setOnClickListener(l);
        btnDao2.setOnClickListener(l);
        btnDao3.setOnClickListener(l);
        goLiveBtn.setOnClickListener((v)->{
            name = nameInput.getText().toString();
            if(name.isEmpty()){
                nameInput.setError("Thieu Name !!!");
                nameInput.requestFocus();
                return;
            }
            startMeeting();
        });
        goNextLive.setOnClickListener((v)->{
            name = nameInput1.getText().toString();
            if(name.isEmpty()){
                nameInput1.setError("Nhap Name Cai Ban Ui");
                nameInput1.requestFocus();
                return;
            }
            startMeeting1();
        });
        listviewBasic = findViewById(R.id.listviewlive);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        listviewBasic.setOnItemClickListener((parent, view, position, id) -> {
            String ID_live = itemsAdapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, JoinActivity.class);
            intent.putExtra("Live_id", ID_live);

            startActivity(intent);
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        items.clear();
        Call<GetLiveResponseAPIModel> call = apiInterface.getLiveList();
        call.enqueue(new Callback<GetLiveResponseAPIModel>() {
            @Override
            public void onResponse(Call<GetLiveResponseAPIModel> call, Response<GetLiveResponseAPIModel> response) {
                GetLiveResponseAPIModel reponse = response.body();
                for( int i = 0; i< reponse.getData().size();i++){
                    items.add(String.valueOf(reponse.getData().get(i).getidLive()));

                }
                itemsAdapter = new ArrayAdapter<String>(MainActivity.this , android.R.layout.simple_list_item_1, items);
                listviewBasic.setAdapter(itemsAdapter);
            }

            @Override
            public void onFailure(Call<GetLiveResponseAPIModel> call, Throwable t) {

            }
        });
    }

    void startMeeting(){
        boolean isHost = true;
        LiveID = generateLiveID();
        userID = UUID.randomUUID().toString();

        Intent intent = new Intent(MainActivity.this, LiveActivity.class);
        intent.putExtra("user_id", userID);
        intent.putExtra("name", name);
        intent.putExtra("Live_id", LiveID);
        intent.putExtra("host", isHost);
        startActivity(intent);
    }
    void startMeeting1(){
        LiveID = getIntent().getStringExtra(  "Live_id");
        userID = UUID.randomUUID().toString();
        Intent intent = new Intent(MainActivity.this, NextLiveActivity.class);
        intent.putExtra("user_id", userID);
        intent.putExtra("name", name);
        intent.putExtra("host", false);
        startActivity(intent);
    }

//    String generateLiveID() {
//        StringBuilder id = new StringBuilder();
//        while (id.length() != 5) {
//            int random = new Random().nextInt(19);
//            id.append(random);
//        }
//        return id.toString();
//    }
    String generateLiveID() {
        int min = 10000;
        int max = 99999;
        int randomValue = new Random().nextInt(max - min + 1) + min;
        return String.valueOf(randomValue);
    }
    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnHome || view.getId() == R.id.btnHome2 || view.getId() == R.id.btnHome3) {
                Trang1.setVisibility(View.VISIBLE);
                Trang2.setVisibility(View.INVISIBLE);
                Trang3.setVisibility(View.INVISIBLE);
                onStart();
            }
            if (view.getId() == R.id.btnLive || view.getId() == R.id.btnLive2 || view.getId() == R.id.btnLive3) {
                Trang1.setVisibility(View.INVISIBLE);
                Trang2.setVisibility(View.VISIBLE);
                Trang3.setVisibility(View.INVISIBLE);
            }
            if (view.getId() == R.id.btnDao || view.getId() == R.id.btnDao2 || view.getId() == R.id.btnDao3) {
                Trang1.setVisibility(View.INVISIBLE);
                Trang2.setVisibility(View.INVISIBLE);
                Trang3.setVisibility(View.VISIBLE);
            }
        }
    };
}