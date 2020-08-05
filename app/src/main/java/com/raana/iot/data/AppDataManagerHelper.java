package com.raana.iot.data;


import com.raana.iot.data.model.Detail;
import com.raana.iot.data.model.TempHum;

import retrofit2.Call;
import retrofit2.Callback;

public interface AppDataManagerHelper {
    Call<TempHum> getSensorsData(Callback<TempHum> callback);

    Call<Detail> getDetail(Callback<Detail> callback);
}
