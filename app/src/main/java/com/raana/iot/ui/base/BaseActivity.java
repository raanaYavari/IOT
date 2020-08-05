package com.raana.iot.ui.base;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;

import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.raana.iot.R;
import com.raana.iot.util.ShowLoading;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
        AlertDialog progressDialog;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showErrorMessage(String message) {
        if (message != null) {
            new StyleableToast
                    .Builder(this)
                    .text(message)
                    .textColor(ContextCompat.getColor(this,R.color.color_red))
                    .backgroundColor(ContextCompat.getColor(this,R.color.colorWhite))
                    .stroke(1, ContextCompat.getColor(this,R.color.color_red))
                    .show();
        }
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            new StyleableToast
                    .Builder(this)
                    .text(message)
                    .textColor(ContextCompat.getColor(this,R.color.colorPrimary))
                    .backgroundColor(ContextCompat.getColor(this,R.color.colorWhite))
                    .stroke(1,ContextCompat.getColor(this,R.color.colorPrimaryDark))
                    .show();
        }
    }

    @Override
    public void showProgress() {
        try {
            if (!(this).isFinishing()) {
                if (progressDialog == null || !progressDialog.isShowing())
                    progressDialog = ShowLoading.showLoadingDialog(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideProgress() {
        try {
            if (!(this).isFinishing()) {
                if (progressDialog != null)
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeStatusColor() {
        Window window = getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

   void addIconMoon(AppCompatTextView textView){
       Typeface typeface = Typeface.createFromAsset(getAssets(), "iconmoon.ttf");
       textView.setTypeface(typeface);

   }

}
