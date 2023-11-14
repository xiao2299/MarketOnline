package com.example.marketonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.library.API.APIClient;
import com.example.library.API.APIInterface;
import com.example.library.APIModel.GetLiveResponseAPIModel;
import com.example.library.APIModel.GetProductResponseAPIModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    APIInterface apiInterface;
    ListView listviewBasic;
    ArrayAdapter<String> itemsAdapter;
    List<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        listviewBasic = findViewById(R.id.listviewlive);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        listviewBasic.setOnItemClickListener((parent, view, position, id) -> {
        String ID_live = itemsAdapter.getItem(position);
        Intent intent = new Intent(HomeActivity.this, JoinActivity.class);
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
                itemsAdapter = new ArrayAdapter<String>(HomeActivity.this , android.R.layout.simple_list_item_1, items);
                listviewBasic.setAdapter(itemsAdapter);
            }

            @Override
            public void onFailure(Call<GetLiveResponseAPIModel> call, Throwable t) {

            }
        });
    }

    public void Go_Live(View view) {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void Go_Next_Live(View view) {
        Intent intent = new Intent(HomeActivity.this, HomeLiveActivity.class);
        startActivity(intent);
    }
}