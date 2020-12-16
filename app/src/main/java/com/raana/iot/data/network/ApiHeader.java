package com.raana.iot.data.network;

import com.raana.iot.data.model.BusStation;
import com.raana.iot.data.model.DistanceMatrix;
import com.raana.iot.data.model.Driver;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiHeader {

    @GET("busstations")
    Call<List<BusStation>> getBusStations();

    @GET("getDrivers")
    Call<List<Driver>> getDrivers(@Query("id") String id);

    @GET("distance-matrix")
    Call<DistanceMatrix> distanceApi(@Header("Api-Key") String apiKey, @Query("origins") String origin, @Query("destinations") String destination);


}

