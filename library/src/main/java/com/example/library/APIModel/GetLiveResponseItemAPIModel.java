package com.example.library.APIModel;

public class GetLiveResponseItemAPIModel {

    public int idLive;
    public String name;

    public GetLiveResponseItemAPIModel(int idLive, String name) {
        this.idLive = idLive;
        this.name = name;
    }

    public int getidLive() {
        return idLive;
    }
    public void setidLive(int value) {
        this.idLive = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }


}


