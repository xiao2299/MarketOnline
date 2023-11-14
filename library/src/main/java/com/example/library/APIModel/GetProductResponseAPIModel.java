package com.example.library.APIModel;


import java.io.Serializable;
import java.util.List;

public class GetProductResponseAPIModel  implements Serializable {
    private boolean status;
    private String message;
    private List<GetProductResponseItemAPIModel> data;

    public boolean getStatus() { return status; }
    public void setStatus(boolean value) { this.status = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public List<GetProductResponseItemAPIModel> getData() { return data; }
    public void setData(List<GetProductResponseItemAPIModel> value) { this.data = value; }
}
