package com.raana.iot.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TempHum {
    @SerializedName("out")
    @Expose
    private Out out;

    public Out getOut() {
        return out;
    }

    public void setOut(Out out) {
        this.out = out;
    }
}
