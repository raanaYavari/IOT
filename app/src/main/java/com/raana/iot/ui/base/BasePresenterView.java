package com.raana.iot.ui.base;


public interface BasePresenterView <V extends BaseView> {
    void onAttach(V BaseView);
    V getMvpView();

}

