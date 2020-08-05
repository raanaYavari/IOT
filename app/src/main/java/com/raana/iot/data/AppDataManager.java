package com.raana.iot.data;

import android.content.Context;

import com.raana.iot.data.model.Detail;
import com.raana.iot.data.model.TempHum;
import com.raana.iot.data.network.ApiBaseCreator;
import com.raana.iot.data.network.ApiHeader;

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
            myContext=context;
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
    public Call<TempHum> getSensorsData(final Callback<TempHum> callback){
        Call<TempHum> call=ApiBaseCreator.getApiHeader("").getSensorsData("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJURVNUMiIsInVzciI6Ik1hbGloZWg3NyJ9.iEGEE4rrbx_o7qrK-VWMEcpj8KoOfrc3FWaCWVvNVWo");
        call.enqueue(new Callback<TempHum>() {
            @Override
            public void onResponse(Call<TempHum> call, Response<TempHum> response) {
                callback.onResponse(call,response);
            }

            @Override
            public void onFailure(Call<TempHum> call, Throwable t) {
                callback.onFailure(call,t);
            }
        });
        return call;
    }


    @Override
    public Call<Detail> getDetail(final Callback<Detail> callback){
        Call<Detail> call=ApiBaseCreator.getApiHeader("").getDetail("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJURVNUMiIsInVzciI6Ik1hbGloZWg3NyJ9.iEGEE4rrbx_o7qrK-VWMEcpj8KoOfrc3FWaCWVvNVWo");
        call.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                callback.onResponse(call,response);
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                callback.onFailure(call,t);
            }
        });
        return call;
    }



//    @Override
//    public Call<LoginModelResponse> doLoginApiCall(String userName, String password, final Callback<LoginModelResponse> callback) {
//        Call<LoginModelResponse> call = ApiBaseCreator.getApiHeader("").doLoginCall("password", userName, password, "asdf");
//        call.enqueue(new Callback<LoginModelResponse>() {
//            @Override
//            public void onResponse(Call<LoginModelResponse> call, Response<LoginModelResponse> response) {
//                if (response.isSuccessful()) {
//                    appPreferences.saveAccessToken(response.body().getAccessToken());
//                    appPreferences.saveRefreshToken(response.body().getRefreshToken());
//                    setLogin(true);
//                }
//                callback.onResponse(call, response);
//
//            }
//
//            @Override
//            public void onFailure(Call<LoginModelResponse> call, Throwable t) {
//                callback.onFailure(call, t);
//
//            }
//
//        });
//        return call;
//    }
//
}
