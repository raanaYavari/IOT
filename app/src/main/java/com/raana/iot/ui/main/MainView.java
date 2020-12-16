package com.raana.iot.ui.main;


import com.raana.iot.data.model.BusStation;
import com.raana.iot.data.model.Driver;
import com.raana.iot.ui.base.BaseView;

import java.util.List;

public interface MainView extends BaseView {
    void initWorkPlaceItem(List<BusStation> myBusStationList);

    void initDrivers(List<Driver> drivers);
}
