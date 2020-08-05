package com.raana.iot.ui.main;

import android.util.Log;

import com.raana.iot.data.AppDataManager;
import com.raana.iot.data.model.Detail;
import com.raana.iot.data.model.TempHum;
import com.raana.iot.ui.base.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter<V extends MainView> extends BasePresenter<V> implements MainPresenterView {

    static int i,j=0;
    public void getTemperature(){
        AppDataManager.getInstance(getMvpView().getContext()).getSensorsData(new Callback<TempHum>() {
            @Override
            public void onResponse(Call<TempHum> call, Response<TempHum> response) {
                if(response.isSuccessful()){
                    getMvpView().setData(i,response.body().getOut().getTemperature());
                    i++;
                }
            }

            @Override
            public void onFailure(Call<TempHum> call, Throwable t) {

            }
        });
    }
    public void getHumadity(){
        AppDataManager.getInstance(getMvpView().getContext()).getSensorsData(new Callback<TempHum>() {
            @Override
            public void onResponse(Call<TempHum> call, Response<TempHum> response) {
                if(response.isSuccessful()){
                    getMvpView().setHumadityData(j,response.body().getOut().getHumadity());
                    j++;
                }
            }

            @Override
            public void onFailure(Call<TempHum> call, Throwable t) {

            }
        });
    }

    public void getDetail(){
        AppDataManager.getInstance(getMvpView().getContext()).getDetail(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                if(response.isSuccessful()){
                        getMvpView().setOnMap(response.body().getConnection().getLocation());
                }
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                Log.i("tag",t.getMessage());
            }
        });
    }
}
