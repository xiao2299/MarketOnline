package com.example.marketonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.library.API.APIClient;
import com.example.library.API.APIInterface;
import com.example.library.APIModel.GetLiveResponseAPIModel;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingConfig;
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NextLiveActivity extends AppCompatActivity {
    EditText txtMessage;
    HubConnection hubConnection;
    String userID, name;
    TextView LiveIdText;
    String LiveID;
    ListView listviewchat;
    boolean isHost = false;
    List<String> items = new ArrayList<>();
    List<Integer> idLiveList = new ArrayList<>();
    ArrayAdapter<String> itemsAdapter;
    APIInterface apiInterface;
    int currentItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_live);

        userID = getIntent().getStringExtra("user_id");
        name = getIntent().getStringExtra("name");
        isHost = getIntent().getBooleanExtra("host", false);

        LiveIdText = findViewById(R.id.live_id_textview);

        txtMessage = findViewById(R.id.txtMessage);
        listviewchat = findViewById(R.id.listviewchat);
        itemsAdapter = new ArrayAdapter<String>(NextLiveActivity.this , android.R.layout.simple_list_item_1, items);
        listviewchat.setAdapter(itemsAdapter);
        hubConnection =  HubConnectionBuilder.create("https://othertanpage51.conveyor.cloud/chatHub")
                .build();

        hubConnection.on("ReceiveMessage", (user, message) -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.v("log:", user + ":" + message);
                    items.add(user + ":" + message);
                    itemsAdapter.notifyDataSetChanged();
                }
            });

        }, String.class, String.class);
        hubConnection.start();



        apiInterface = APIClient.getClient().create(APIInterface.class);
        countHost();
        if (!idLiveList.isEmpty()) {
            LiveID = String.valueOf(idLiveList.get(currentItemIndex));
        }


    }

    void countHost() {
        Call<GetLiveResponseAPIModel> call = apiInterface.getLiveList();
        call.enqueue(new Callback<GetLiveResponseAPIModel>() {
            @Override
            public void onResponse(Call<GetLiveResponseAPIModel> call, Response<GetLiveResponseAPIModel> response) {
                GetLiveResponseAPIModel reponse = response.body();

                if(getIntent().getIntExtra("Item_live",-100) == -100 ){
                    currentItemIndex = 0;
                }else{
                    currentItemIndex = getIntent().getIntExtra("Item_live",1);
                }

                idLiveList.clear();

                for (int i = 0; i < reponse.getData().size(); i++) {
                    idLiveList.add(reponse.getData().get(i).getidLive());
                }

                if (!idLiveList.isEmpty()) {
                    LiveID = String.valueOf(idLiveList.get(currentItemIndex));
                    LiveIdText.setText(LiveID);
                    addFragment();
                }
            }

            @Override
            public void onFailure(Call<GetLiveResponseAPIModel> call, Throwable t) {

            }
        });
    }

    void addFragment() {
        ZegoUIKitPrebuiltLiveStreamingConfig config;
        if (isHost) {
            config = ZegoUIKitPrebuiltLiveStreamingConfig.host();
        } else {
            config = ZegoUIKitPrebuiltLiveStreamingConfig.audience();
        }

        ZegoUIKitPrebuiltLiveStreamingFragment fragmen = ZegoUIKitPrebuiltLiveStreamingFragment.newInstance(
                AppConstants.appId, AppConstants.appSign, userID, name, LiveID, config);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmen)
                .commitNow();
    }

    public void Next_Meeting(View view) {
        if (currentItemIndex < idLiveList.size() - 1) {
            currentItemIndex++;
        }

            // Tạo intent để khởi tạo lại activity với chỉ mục mới
            Intent intent = new Intent(this, NextLiveActivity.class);
            intent.putExtra("user_id", userID);
            intent.putExtra("name", name);
            intent.putExtra("host", isHost);
            intent.putExtra("Item_live", currentItemIndex);
            startActivity(intent);

            finish();

    }
    public void Back_Meeting(View view) {
        if (currentItemIndex > 0) {
            currentItemIndex -- ;
        }
            Intent intent = new Intent(this, NextLiveActivity.class);
            intent.putExtra("user_id", userID);
            intent.putExtra("name", name);
            intent.putExtra("host", isHost);
            intent.putExtra("Item_live", currentItemIndex);
            startActivity(intent);

            finish();  // Kết thúc activity hiện tại


    }

    public void SendMessage(View view) {
        String userName = name;
        String strMessage = txtMessage.getText().toString();
        if(strMessage.trim().isEmpty() || strMessage.isEmpty())
            return;
        hubConnection.invoke("SendMessage", userName, strMessage);
        txtMessage.setText("");
    }


}
