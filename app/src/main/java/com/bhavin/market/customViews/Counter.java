package com.bhavin.market.customViews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.bhavin.market.R;
import com.google.android.material.button.MaterialButton;

public class Counter extends LinearLayout {

    public interface ChangeListener{
        void onValueChange(String newVal);
    }

    private ChangeListener changeListener;

    private final String[] unitArray = new String[]{"Kg", "L", "g","m", ""};

    public static final int KG = 0;
    public static final int LITRE = 1;
    public static final int GRAMS = 2;
    public static final int LENGTH = 3;
    public static final int UNIT = 4;

    public static final int INTEGER = 0;
    public static final int FLOATING = 1;

    private String unit;
    private float counter, changeBy, minCounterValue, buttonSize, buttonCornerRadius;
    private int buttonBackgroundColor, textColor, iconsColor;
    private int counterValueType, strokeWidth;
    private boolean style2 = false;

    private AttributeSet attributeSet;
    private int styleResource;
    private int styleAttr;

    private MaterialButton add, minus;
    private TextView counterText, unitText;

    public Counter(Context context) {
        super(context);
        createView();
    }

    public Counter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        decodeAttributes(attrs);
        createView();
    }

    public Counter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        decodeAttributes(attrs);
        createView();
    }

    private void decodeAttributes(AttributeSet attrs) {

        attributeSet = attrs;
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                            attrs,
                            R.styleable.Counter, 0,0);

        try{
            unit = unitArray[a.getInt(R.styleable.Counter_unit, UNIT)];
            changeBy = a.getFloat(R.styleable.Counter_changeByValue, 1);
            buttonBackgroundColor = a.getColor(R.styleable.Counter_buttonBackgroundColor, Color.BLUE);
            textColor = a.getColor(R.styleable.Counter_textColor, Color.BLACK);
            iconsColor = a.getColor(R.styleable.Counter_iconColor, Color.WHITE);
            minCounterValue = a.getFloat(R.styleable.Counter_minCounterValue, 0);
            buttonSize = a.getDimensionPixelSize(R.styleable.Counter_counterButtonSize, 100);
            buttonCornerRadius = a.getDimensionPixelSize(R.styleable.Counter_buttonCornerRadius, 20);
            counterValueType = a.getInt(R.styleable.Counter_counterValueType, 0);
            style2 = a.getBoolean(R.styleable.Counter_useStyle2, false);
            strokeWidth = a.getDimensionPixelSize(R.styleable.Counter_style2StrokeWidth, 5);
            counter = minCounterValue;
        }finally {
            a.recycle();
        }

    }

    private void createView() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        minus = addButton(R.drawable.ic_minus);
        minus.setOnClickListener(v -> onClickMinus());

        if(counterValueType == INTEGER){
            counterText = addText(String.valueOf((int)counter));
        }else{
            counterText = addText(String.valueOf(counter));
        }
        unitText = addText((unit.isEmpty())?unit:(" "+unit));

        add = addButton(R.drawable.ic_add);
        add.setOnClickListener(v -> onClickPlus());
    }

    private void onClickPlus() {
        counter = counter + changeBy;
        String val;
        if(counterValueType == INTEGER){
            val = String.valueOf((int)counter);
        }else{
            val = String.valueOf(counter);
        }
        if(changeListener != null) changeListener.onValueChange(val);
        counterText.setText(val);
    }

    private void onClickMinus() {
        if(counter-changeBy >= minCounterValue){
            counter = counter - changeBy;
            String val;
            if(counterValueType == INTEGER){
                val = String.valueOf((int)counter);
            }else{
                val = String.valueOf(counter);
            }
            if(changeListener != null) changeListener.onValueChange(val);
            counterText.setText(val);
        }else{
            Toast.makeText(getContext(), "Min Value", Toast.LENGTH_SHORT).show();
        }
    }

    private TextView addText(String value) {
        int margin = getResources().getDimensionPixelSize(R.dimen.buttonExtraHeight);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, margin, 0, margin);

        TextView textView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textView = new TextView(getContext(), attributeSet, styleAttr, styleResource);
        }else{
            textView = new TextView(getContext());
        }
        textView.setText(value);
        textView.setTextColor(textColor);
        textView.setTextSize(0.3f*buttonSize);
        textView.setLayoutParams(params);

        addView(textView);

        return textView;
    }

    private MaterialButton addButton(int icon) {

        int extra = getResources().getDimensionPixelSize(R.dimen.buttonExtraHeight);
        MaterialButton button = new MaterialButton(getContext());
        LayoutParams params = new LayoutParams((int) buttonSize, (int) (buttonSize+extra));
        params.setMargins(extra,extra,extra,extra);
        button.setLayoutParams(params);

        button.setIconResource(icon);
        button.setIconSize((int) (buttonSize-extra));
        button.setIconGravity(MaterialButton.ICON_GRAVITY_TOP);

        button.setCornerRadius((int) buttonCornerRadius);

        if(icon == R.drawable.ic_add){
            button.setIconTint(ColorStateList.valueOf(iconsColor));
            button.setBackgroundColor(buttonBackgroundColor);
        }else{
            if(style2){
                button.setBackgroundColor(Color.WHITE);
                button.setIconTint(ColorStateList.valueOf(buttonBackgroundColor));
                button.setStrokeWidth(strokeWidth);
                button.setStrokeColor(ColorStateList.valueOf(buttonBackgroundColor));
            }else{
                button.setIconTint(ColorStateList.valueOf(iconsColor));
                button.setBackgroundColor(buttonBackgroundColor);
            }
        }

        addView(button);

        return button;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getCounterValue() {
        return counter;
    }

    public void setCounterValue(float counter) {
        this.counter = counter;
    }

    public float getChangeByValue() {
        return changeBy;
    }

    public void setChangeByValue(float changeBy) {
        this.changeBy = changeBy;
    }

    public float getMinCounterValue() {
        return minCounterValue;
    }

    public void setMinCounterValue(float minCounterValue) {
        this.minCounterValue = minCounterValue;
    }

    public float getButtonSize() {
        return buttonSize;
    }

    public void setButtonSize(float buttonSize) {
        this.buttonSize = buttonSize;
    }

    public float getButtonCornerRadius() {
        return buttonCornerRadius;
    }

    public void setButtonCornerRadius(float buttonCornerRadius) {
        this.buttonCornerRadius = buttonCornerRadius;
    }

    public int getButtonBackgroundColor() {
        return buttonBackgroundColor;
    }

    public void setButtonBackgroundColor(int buttonBackgroundColor) {
        this.buttonBackgroundColor = buttonBackgroundColor;
    }

    public void setChangeListener(ChangeListener changeListener){
        this.changeListener = changeListener;
    }

    public int getTextColor( ) {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getIconsColor() {
        return iconsColor;
    }

    public void setIconsColor(int iconsColor) {
        this.iconsColor = iconsColor;
    }
}
