package com.raana.iot.ui.base;


public class BasePresenter <V extends BaseView> implements BasePresenterView<V> {
    private V mBaseView;

    @Override
    public void onAttach(V BaseView) {
        mBaseView = BaseView;
    }

    @Override
    public V getMvpView() {
        return mBaseView;
    }



}

