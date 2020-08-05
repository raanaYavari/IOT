package com.raana.iot.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Out {

    @SerializedName("temperature")
    @Expose
    private int temperature;
    @SerializedName("humadity")
    @Expose
    private int humadity;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumadity() {
        return humadity;
    }

    public void setHumadity(int humadity) {
        this.humadity = humadity;
    }
}
