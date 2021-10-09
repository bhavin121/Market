package com.bhavin.market.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.bhavin.market.R;

public class SquareImageView extends androidx.appcompat.widget.AppCompatImageView {

    private float heightOfWidth = -1f;
    private float widthOfHeight = -1f;

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        extractAttrs(attrs);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        extractAttrs(attrs);
    }

    private void extractAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SquareImageView,
                0,0
        );

        heightOfWidth = a.getFloat(R.styleable.SquareImageView_heightOfWidth, -1);
        widthOfHeight = a.getFloat(R.styleable.SquareImageView_widthOfHeight, -1);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(heightOfWidth>0){
            heightMeasureSpec = (int) (heightOfWidth*widthMeasureSpec);
        }else if(widthOfHeight>0){
            widthMeasureSpec = (int) (widthOfHeight*heightMeasureSpec);
        }else{
            heightMeasureSpec = widthMeasureSpec;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
