package com.raana.iot.ui.base;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;


public interface BaseView {
    Context getContext();
    void showErrorMessage(String message);
    void showMessage(String message);
    void showProgress();
    void hideProgress();




}

