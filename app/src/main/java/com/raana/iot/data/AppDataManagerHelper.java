package com.raana.iot.data;


import com.raana.iot.data.model.BusStation;
import com.raana.iot.data.model.DistanceMatrix;
import com.raana.iot.data.model.Driver;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public interface AppDataManagerHelper {

    Call<List<BusStation>> getBusStations(Callback<List<BusStation>> callback);

    Call<List<Driver>> getDrivers(String busStationId, Callback<List<Driver>> callback);

    Call<DistanceMatrix> distanceApi(String origin, String destination, Callback<DistanceMatrix> callback);
}
