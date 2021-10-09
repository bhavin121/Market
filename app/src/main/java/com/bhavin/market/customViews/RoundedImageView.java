package com.bhavin.market.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import com.bhavin.market.R;

/**
 * Rounded Image View is an child of AppCompactImageView
 * with an extra added feature of making image view
 * corner rounded. You can assign this radius
 * IN XML - app:imageCornerRadius="12dp"
 * IN JAVA - setImageCornerRadius(12); value is in pixels
 *
 * You Cannot assign background to this image view
 * It will be ignored if not and shape drawable
 */

public class RoundedImageView extends AppCompatImageView {

    private int cornerRadius;

    public RoundedImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public RoundedImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        extractAttrs(context, attrs);
        init();
    }

    public RoundedImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        extractAttrs(context, attrs);
        init();
    }

    private void extractAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RoundedImageView,
                0,0
        );

        cornerRadius = a.getDimensionPixelSize(R.styleable.RoundedImageView_imageCornerRadius, 50);
        a.recycle();
    }

    private void init() {
        float r = cornerRadius;
        ShapeDrawable shapeDrawable = new ShapeDrawable(
                new RoundRectShape(new float[]{r,r,r,r,r,r,r,r},null,null)
        );
        shapeDrawable.getPaint().setColor(Color.WHITE);
        setBackground(shapeDrawable);
        setClipToOutline(true);
    }

    @Override
    public void setBackground(Drawable background) {
        // restricting for only shape drawable
        // ignores any other type of drawables
        if(background instanceof ShapeDrawable){
            super.setBackground(background);
        }
    }

    /*
     * Getter and setter for corner radius
     */

    public void setImageCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        init();
    }

    public int getImageCornerRadius() {
        return cornerRadius;
    }
}

