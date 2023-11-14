package com.example.marketonline.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.library.APIModel.GetLiveResponseItemAPIModel;
import com.example.marketonline.R;

import java.util.ArrayList;

public class LiveViewAdapter extends BaseAdapter {
    public ArrayList<GetLiveResponseItemAPIModel> listLive;
    LiveViewAdapter(ArrayList<GetLiveResponseItemAPIModel> listLive){
        this.listLive = listLive;
    }

    @Override
    public int getCount() {
      return listLive.size();
    }

    @Override
    public Object getItem(int position){
        return listLive.get(position);
    }
    @Override
    public long getItemId(int position){
        return listLive.get(position).idLive;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewLive;
        if(convertView == null){
            viewLive = View.inflate(parent.getContext(), R.layout.live_on,null);
        }else{
            viewLive = convertView;
        }
        GetLiveResponseItemAPIModel live = (GetLiveResponseItemAPIModel) getItem(position);
        ((TextView) viewLive.findViewById(R.id.ID_Live)).setText(String.format("ID = %d",live.idLive ));
        ((TextView) viewLive.findViewById(R.id.Name_user_live)).setText(String.format("Tên chủ live : %s", live.name));


        return viewLive;
    }
}
