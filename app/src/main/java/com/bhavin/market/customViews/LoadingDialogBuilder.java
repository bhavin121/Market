package com.bhavin.market.customViews;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import com.bhavin.market.databinding.LoadingDialogLayoutBinding;

public abstract class LoadingDialogBuilder {

    public static AlertDialog build(@NonNull Context context){
        LoadingDialogLayoutBinding binding = LoadingDialogLayoutBinding.inflate(LayoutInflater.from(context));
        View view = binding.getRoot();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view)
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return dialog;
    }

}
