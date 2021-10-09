package com.bhavin.market.customViews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;

public class DarkImageView extends AppCompatImageView {

    public DarkImageView(Context context) {
        super(context);
        init();
    }

    public DarkImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DarkImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setColorFilter(Color.rgb(90,90,90), PorterDuff.Mode.MULTIPLY);
    }
}
