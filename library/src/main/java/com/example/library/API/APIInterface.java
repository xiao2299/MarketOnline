package com.example.library.API;

import com.example.library.APIModel.DeleteLiveResponseAPIModel;
import com.example.library.APIModel.GetLiveResponseAPIModel;
import com.example.library.APIModel.GetProductResponseAPIModel;
import com.example.library.APIModel.InsertLiveResponseAPIModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("api/Sales/get-product-list")
    Call<GetProductResponseAPIModel> getProductList();
    @GET("api/Sales/get-Live-list")
    Call<GetLiveResponseAPIModel> getLiveList();
    @POST("api/Sales/insert-live")
    Call<InsertLiveResponseAPIModel> InsertLive(@Query("idLive") int IDLive, @Query("name") String Name );
    @POST("api/Sales/delete-live")
    Call<DeleteLiveResponseAPIModel> deleteLive(@Query("idLive") int Liveid );



}
