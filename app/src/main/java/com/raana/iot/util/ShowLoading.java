package com.raana.iot.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;


import androidx.appcompat.app.AlertDialog;

import com.raana.iot.R;

public class ShowLoading {
    ShowLoading() {
    }

    public static AlertDialog showLoadingDialog(Context context) {
        try {
            AlertDialog.Builder progressDialogBuilder;
            AlertDialog progressDialog;

            progressDialogBuilder = new AlertDialog.Builder(context);
            progressDialog = progressDialogBuilder.create();
            progressDialog.show();
            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            progressDialog.setContentView(R.layout.show_progress);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            return progressDialog;
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
            return null;
        }

    }

}
