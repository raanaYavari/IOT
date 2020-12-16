package com.raana.iot.data;

import android.content.Context;

import com.raana.iot.data.model.BusStation;
import com.raana.iot.data.model.DistanceMatrix;
import com.raana.iot.data.model.Driver;
import com.raana.iot.data.network.ApiBaseCreator;
import com.raana.iot.data.network.ApiHeader;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppDataManager implements AppDataManagerHelper {

    private static AppDataManager appDataManager;
    private final ApiHeader apiHeader;
    static Context myContext;

    public static AppDataManager getInstance(Context context) {
        if (appDataManager == null) {
            appDataManager = new AppDataManager(ApiBaseCreator.getApiHeader(""));
            myContext = context;
        }
        return appDataManager;
    }

    public AppDataManager(ApiHeader apiHeader) {
        this.apiHeader = apiHeader;
    }

    public AppDataManager(Context context) {
        this.apiHeader = null;
    }

    //<<<<<<<<< API CALLS ARE HERE >>>>>>>>

    @Override
    public Call<List<BusStation>> getBusStations(final Callback<List<BusStation>> callback) {
        Call<List<BusStation>> call = ApiBaseCreator.getApiHeader("").getBusStations();
        call.enqueue(new Callback<List<BusStation>>() {
            @Override
            public void onResponse(Call<List<BusStation>> call, Response<List<BusStation>> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<BusStation>> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });

        return call;
    }


    @Override
    public Call<List<Driver>> getDrivers(String busStationId, final Callback<List<Driver>> callback) {
        Call<List<Driver>> call = ApiBaseCreator.getApiHeader("").getDrivers(busStationId);
        call.enqueue(new Callback<List<Driver>>() {
            @Override
            public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });

        return call;
    }

    @Override
    public Call<DistanceMatrix> distanceApi(String origin, String destination, final Callback<DistanceMatrix> callback) {
        Call<DistanceMatrix> call = ApiBaseCreator.getNeshanHeader().distanceApi("service.oC6YmyHz4UNtCIzC70bBiFfUNlp8I56LEIiFLiPO",origin,destination);
        call.enqueue(new Callback<DistanceMatrix>() {
            @Override
            public void onResponse(Call<DistanceMatrix> call, Response<DistanceMatrix> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<DistanceMatrix> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });

        return call;
    }

}
