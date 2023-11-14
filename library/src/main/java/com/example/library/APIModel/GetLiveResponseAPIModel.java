package com.example.library.APIModel;


import java.io.Serializable;
import java.util.List;

public class GetLiveResponseAPIModel {
    private boolean status;
    private String message;
    private List<GetLiveResponseItemAPIModel> data;

    public boolean getStatus() { return status; }
    public void setStatus(boolean value) { this.status = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public List<GetLiveResponseItemAPIModel> getData() { return data; }
    public void setData(List<GetLiveResponseItemAPIModel> value) { this.data = value; }
}
