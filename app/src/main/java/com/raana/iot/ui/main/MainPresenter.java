package com.raana.iot.ui.main;

import android.util.Log;

import com.raana.iot.data.AppDataManager;
import com.raana.iot.data.model.BusStation;
import com.raana.iot.data.model.DistanceMatrix;
import com.raana.iot.data.model.Driver;
import com.raana.iot.ui.base.BasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter<V extends MainView> extends BasePresenter<V> implements MainPresenterView {

    public void getBusStations() {
        AppDataManager.getInstance(getMvpView().getContext()).getBusStations(new Callback<List<BusStation>>() {
            @Override
            public void onResponse(Call<List<BusStation>> call, Response<List<BusStation>> response) {
                if (response.isSuccessful())
                    getMvpView().initWorkPlaceItem(response.body());
            }

            @Override
            public void onFailure(Call<List<BusStation>> call, Throwable t) {

            }
        });
    }

    public void getDivers(String busStationId) {
        AppDataManager.getInstance(getMvpView().getContext()).getDrivers(busStationId,new Callback<List<Driver>>() {
            @Override
            public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
                if (response.isSuccessful())
                    getMvpView().initDrivers(response.body());
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
                Log.i("error",t.getMessage());
            }
        });
    }

    public void getDivers(String origin,String destination) {
        AppDataManager.getInstance(getMvpView().getContext()).distanceApi(origin, destination, new Callback<DistanceMatrix>() {
            @Override
            public void onResponse(Call<DistanceMatrix> call, Response<DistanceMatrix> response) {

            }

            @Override
            public void onFailure(Call<DistanceMatrix> call, Throwable t) {

            }
        });
    }
}
