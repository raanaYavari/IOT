package com.raana.iot.ui.main;


import com.raana.iot.data.model.Location;
import com.raana.iot.ui.base.BaseView;

public interface MainView extends BaseView {


    void setData(int count, int range);

    void setHumadityData(int count, int range);

    void setOnMap(Location location);
}
