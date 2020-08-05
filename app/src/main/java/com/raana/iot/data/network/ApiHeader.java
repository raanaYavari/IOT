package com.raana.iot.data.network;

import com.raana.iot.data.model.Detail;
import com.raana.iot.data.model.TempHum;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiHeader {

    @GET("v2/users/Maliheh77/devices/TEST_IOT/TempHum")
    Call<TempHum> getSensorsData(@Header("Authorization") String token);

     @GET("https://api.thinger.io/v1/users/Maliheh77/devices/TEST_IOT")
     Call<Detail> getDetail(@Header("Authorization") String token);


}

