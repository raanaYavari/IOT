package com.raana.iot.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistanceMatrix {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("rows")
    @Expose
    private List<Element> rows;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Element> getRows() {
        return rows;
    }

    public void setRows(List<Element> rows) {
        this.rows = rows;
    }
}
