package com.raana.iot.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Element {
    @SerializedName("elements")
    @Expose
    private List<DistanceResult> elements;

    public List<DistanceResult> getElements() {
        return elements;
    }

    public void setElements(List<DistanceResult> elements) {
        this.elements = elements;
    }
}
