package com.bhavin.market.customViews;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bhavin.market.R;

public class CurvedPad extends View {

    /*
     * Attributes
     */

    // size and radius
    private int curvePosition=400;
    private int curveWidth=200;
    private int curveDepth=80;
    private int height, width;
    private int cornerRadius = 80;
    private int circleRadius = 15;

    // Path object for this curved shape
    private final Path path = new Path();

    // Paints for shape and circle
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // Color for background
    private int backgroundColor = getResources().getColor(R.color.purple_500);

    // Animators
    private ObjectAnimator centerAnimator, depthAnimator;

    /*********************************************************
     *  Constructors
     *********************************************************/

    public CurvedPad(Context context) {
        super(context);
        initView();
    }

    public CurvedPad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CurvedPad(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CurvedPad(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    /**
     *  Overridden Methods
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        createPath();
        canvas.drawPath(path, paint);
        canvas.drawCircle(curvePosition, 25, circleRadius, circlePaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        height = getHeight();
        width = getWidth();
    }

    /**
     * Private Methods
     */

    /*
     * This method initializes both paints
     * Called through constructors
     */

    private void initView() {
        paint.setColor(backgroundColor);
        paint.setPathEffect(new CornerPathEffect(cornerRadius));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);

        circlePaint.setColor(getResources().getColor(R.color.pink));
    }

    /*
     * This method creates path object for background
     * Called before each draw
     */

    private void createPath() {
        path.reset();
        path.moveTo(0,height/2f);
        path.lineTo(0, height);
        path.lineTo(width, height);
        path.lineTo(width,0);
        path.lineTo(curvePosition+curveWidth/2f, 0);
        path.lineTo(curvePosition, curveDepth);
        path.lineTo(curvePosition-curveWidth/2f, 0);
        path.lineTo(0,0);
        path.lineTo(0, height/2f);
    }

    /**
     * Public methods
     */

    /*
     * This method is used for animated movement of
     * depth curve center
     */
    public void moveCenterTo(int pos){
        /*
         * If animator is already running close them
         */
        if(centerAnimator != null && centerAnimator.isRunning()){
            centerAnimator.end();
        }
        if(depthAnimator != null && depthAnimator.isRunning()){
            depthAnimator.end();
        }
        /*
         * Now creating a new animator from current values to given pos
         */
        centerAnimator = ObjectAnimator.ofInt(this, "curvePosition", curvePosition, pos);
        centerAnimator.setDuration(200);
        centerAnimator.setInterpolator(new OvershootInterpolator(1));
        centerAnimator.start();

        depthAnimator = ObjectAnimator.ofInt(this,"curveDepth", curveDepth, curveDepth/5, curveDepth);
        depthAnimator.setDuration(200);
        depthAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        depthAnimator.start();
    }

    /*
     * Basic Getter and Setters
     */
    public void setCurvePosition(int curvePosition) {
        this.curvePosition = curvePosition;
        invalidate();
    }

    public void setCurveDepth(int curveDepth) {
        this.curveDepth = curveDepth;
    }

    public int getCurvePosition() {
        return curvePosition;
    }

    public int getCurveWidth() {
        return curveWidth;
    }

    public void setCurveWidth(int curveWidth) {
        this.curveWidth = curveWidth;
    }

    public int getCurveDepth() {
        return curveDepth;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        paint.setColor(backgroundColor);
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        paint.setPathEffect(new CornerPathEffect(cornerRadius));
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
        invalidate();
    }

    public void setCircleColor(int color) {
        this.circlePaint.setColor(color);
        invalidate();
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public Paint getCirclePaint() {
        return circlePaint;
    }
}
