package com.example.marketonline;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.APIModel.DeleteLiveResponseAPIModel;
import com.example.library.APIModel.InsertLiveResponseAPIModel;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingConfig;
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingFragment;

import java.util.ArrayList;
import java.util.List;

import com.example.library.API.APIClient;
import com.example.library.API.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveActivity extends AppCompatActivity {

    String userID, name, LiveID;
    boolean isHost;

    TextView LiveIdText;
    EditText txtMessage;
    ListView listviewchat;
    ArrayAdapter<String> itemsAdapter;
    List<String> items = new ArrayList<>();
    HubConnection hubConnection;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        LiveIdText = findViewById(R.id.live_id_textview);

        userID = getIntent().getStringExtra(  "user_id");
        name = getIntent().getStringExtra( "name");
        LiveID = getIntent().getStringExtra(  "Live_id");
        isHost = getIntent().getBooleanExtra("host",false);

        LiveIdText.setText(LiveID);
        addFragment();

        listviewchat = findViewById(R.id.listviewchat);
        txtMessage = findViewById(R.id.txtMessage);

        itemsAdapter = new ArrayAdapter<String>(LiveActivity.this , android.R.layout.simple_list_item_1, items);
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
        if(isHost)
            add();
    }

    void add(){
        Call<InsertLiveResponseAPIModel> call = apiInterface.InsertLive(Integer.parseInt(LiveID),name);
        call.enqueue(new Callback<InsertLiveResponseAPIModel>() {
            @Override
            public void onResponse(Call<InsertLiveResponseAPIModel> call, Response<InsertLiveResponseAPIModel> response) {

            }
            @Override
            public void onFailure(Call<InsertLiveResponseAPIModel> call, Throwable t) {
            }
        });
    }

    void addFragment(){
        ZegoUIKitPrebuiltLiveStreamingConfig config;
        if(isHost){
            config = ZegoUIKitPrebuiltLiveStreamingConfig.host();
        }else{
            config = ZegoUIKitPrebuiltLiveStreamingConfig.audience();
        }

        ZegoUIKitPrebuiltLiveStreamingFragment fragmen = ZegoUIKitPrebuiltLiveStreamingFragment.newInstance(
                AppConstants.appId,AppConstants.appSign,userID,name,LiveID,config);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,fragmen)
                .commitNow();

    }
    public void SendMessage(View view) {
        String userName = name;
        String strMessage = txtMessage.getText().toString();
        if(strMessage.trim().isEmpty() || strMessage.isEmpty())
            return;

        hubConnection.invoke("SendMessage", userName, strMessage);
        txtMessage.setText("");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isHost){
            Call<DeleteLiveResponseAPIModel> call = apiInterface.deleteLive(Integer.parseInt(LiveID));
            call.enqueue(new Callback<DeleteLiveResponseAPIModel>() {
                @Override
                public void onResponse(Call<DeleteLiveResponseAPIModel> call, Response<DeleteLiveResponseAPIModel> response) {
                }
                @Override
                public void onFailure(Call<DeleteLiveResponseAPIModel> call, Throwable t) {
                }
            });
        }
    }


}