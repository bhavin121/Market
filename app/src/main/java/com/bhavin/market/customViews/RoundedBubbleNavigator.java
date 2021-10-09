package com.bhavin.market.customViews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import com.bhavin.market.R;

public class RoundedBubbleNavigator extends LinearLayout {

    /*
     * Attributes
     */

    // Text Size, color and font
    private float textSize;
    private int fontFamilyId;
    private ColorStateList textColor;

    // Shape and background
    private int cornerRadius;
    private int circleRadius;
    private int curveWidth;
    private int curveDepth;

    // colors
    private int circleColor;
    private int navigationBackgroundColor;
    private int containerBackgroundColor;

    // menu id
    private int menuId;

    // menu instance
    private Menu menu;

    // attribute sets and styles
    private AttributeSet attributeSet;
    private int defStyleAttr,defStyleRes;

    /*
     * Component views
     */
    private RadioGroup radioGroup;
    private CurvedPad curvedPad;
    private FrameLayout layout;

    /*
     * Listeners
     */
    private OnItemSelectedListener onItemSelectedListener;

    /****************************************************************
     * Constructors
     ****************************************************************/

    public RoundedBubbleNavigator(Context context) {
        super(context);
        initView();
    }

    public RoundedBubbleNavigator(Context context, AttributeSet attrs) {
        super(context, attrs);
        attributeSet = attrs;
        initView();
    }

    public RoundedBubbleNavigator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attributeSet = attrs;
        this.defStyleAttr = defStyleAttr;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundedBubbleNavigator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        attributeSet = attrs;
        this.defStyleAttr = defStyleAttr;
        this.defStyleRes = defStyleRes;
        initView();
    }

    /**
     * Overridden Methods
     */

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        radioGroup.check(menu.getItem(0).getItemId());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(onItemSelectedListener != null){
                    onItemSelectedListener.onSelect(checkedId);
                }
                int center =
                        (int) (radioGroup.findViewById(checkedId).getX()
                                + radioGroup.findViewById(checkedId).getWidth()/2);
                curvedPad.moveCenterTo(center);
            }
        });

        int center =
                (int) (radioGroup.getChildAt(0).getX()
                        + radioGroup.getChildAt(0).getWidth()/2);
        curvedPad.setCurvePosition(center);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        inflateMenu();
        addMenuItems();
    }

    /**
     * private Methods
     */

    /*
     * Extract attributes and save them into variables
     */
    private void extractAttributes(){

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attributeSet, R.styleable.RoundedBubbleNavigator,
                defStyleAttr, defStyleRes
        );

        // text size color and font
        textSize = a.getDimensionPixelSize(R.styleable.RoundedBubbleNavigator_textSize, 100);
        textColor = a.getColorStateList(R.styleable.RoundedBubbleNavigator_android_textColor);
        fontFamilyId = a.getResourceId(R.styleable.RoundedBubbleNavigator_android_fontFamily, 0);

        textSize = textSize/3;

        // Dimensions
        circleRadius = a.getDimensionPixelSize(R.styleable.RoundedBubbleNavigator_indicatorCircleRadius, 15);
        cornerRadius = a.getDimensionPixelSize(R.styleable.RoundedBubbleNavigator_cornerRadius, 40);
        curveDepth = a.getDimensionPixelSize(R.styleable.RoundedBubbleNavigator_curveDepth, 80);
        curveWidth = a.getDimensionPixelSize(R.styleable.RoundedBubbleNavigator_curveWidth, 200);

        // Colors
        circleColor = a.getColor(R.styleable.RoundedBubbleNavigator_circleColor, Color.parseColor("#E91E63"));
        containerBackgroundColor = a.getColor(R.styleable.RoundedBubbleNavigator_containerBackgroundColor, Color.parseColor("#FF6200EE"));
        navigationBackgroundColor = a.getColor(R.styleable.RoundedBubbleNavigator_navigatorBackgroundColor, Color.WHITE);

        // Menu Id
        menuId = a.getResourceId(R.styleable.RoundedBubbleNavigator_menu, R.menu.test);

        // recycling typed array
        a.recycle();

        if(textColor == null){
            int [][]states = {
                    new int[]{android.R.attr.state_checked},
                    new int[]{-android.R.attr.state_checked}
            };
            int []colors = {
                    Color.BLACK,
                    Color.GRAY
            };
            textColor = new ColorStateList(states, colors);
        }

    }

    /*
     * This method add menu items to navigator
     */
    private void addMenuItems() {
        for(int i=0;i<menu.size();i++){
            radioGroup.addView(createRadioButton(menu.getItem(i)));
        }
    }

    /*
     * This method is called when the view is constructed
     * It will add all items that does need the size or
     * position of other views
     * Also it will add background and orientation of layout
     * Calls method extractAttributes()
     */

    private void initView() {
        // set layout orientation
        setOrientation(VERTICAL);

        // extract attributes
        extractAttributes();

        // set rounded corner background
        int r = cornerRadius;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new
                RoundRectShape(new float[]{r,r,r,r,r,r,r,r}, null, null));
        shapeDrawable.getPaint().setColor(navigationBackgroundColor);
        setBackground(shapeDrawable);

        // add base view items
        createRadioGroup();
        setContentArea();
    }

    /*
     * This method constructs navigation bar (Using Radio Group)
     * Adds navigator to parent
     */

    private void createRadioGroup(){
        radioGroup = new RadioGroup(getContext());
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.topMargin = 40;
        params.bottomMargin = 20;

        radioGroup.setLayoutParams(params);
        radioGroup.setOrientation(RadioGroup.HORIZONTAL);

        addView(radioGroup);
    }

    /*
     * This method construct a radio button view object
     * for the provided menu item
     */

    private RadioButton createRadioButton(MenuItem item){

        RadioButton button = new RadioButton(getContext());

        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.weight = 1;

        button.setLayoutParams(params);
        button.setGravity(Gravity.CENTER);
        button.setText(item.getTitle());
        button.setBackgroundColor(Color.TRANSPARENT);

        if(fontFamilyId != 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                button.setTypeface(getContext().getResources().getFont(fontFamilyId));
            }else{
                 try{
                     button.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
                 }catch (Exception e){
                     Log.d("Error", "RoundedBubbleNavigator Font cannot be resolved don't use ttf files directly");
                 }
            }
        }

        button.setTextColor(textColor);
        button.setTextSize(textSize);
        button.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        button.setId(item.getItemId());

        return button;
    }

    /*
     * This method creates and add the bottom content
     * area
     */

    private void setContentArea(){
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        relativeLayout.setLayoutParams(params);

        addCurvedPad(relativeLayout);
        addFrameLayout(relativeLayout);

        addView(relativeLayout);
    }

    /*
     * This method creates and add a Curved Pad the background
     * of the FrameLayout ( This View Provides Curve Movement )
     */

    private void addCurvedPad(RelativeLayout parent){
        curvedPad = new CurvedPad(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        curvedPad.setLayoutParams(params);

        curvedPad.setCurveWidth(curveWidth);
        curvedPad.setCurveDepth(curveDepth);
        curvedPad.setCornerRadius(cornerRadius);
        curvedPad.setBackgroundColor(containerBackgroundColor);
        curvedPad.setCircleRadius(circleRadius);
        curvedPad.setCircleColor(circleColor);

        parent.addView(curvedPad);
    }

    /*
     * This method creates and add a FrameLayout
     * for the fragments to be shown
     */

    private void addFrameLayout(RelativeLayout parent){
        layout = new FrameLayout(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layout.setLayoutParams(params);

        parent.addView(layout);
    }

    /*
     * This method inflates and initializes the menu
     */

    private void inflateMenu(){
        menu = new PopupMenu(getContext(), null).getMenu();
        menu.clear();
        MenuInflater inflater = new MenuInflater(getContext());
        inflater.inflate(menuId, menu);
    }

    /**
     * Public Methods Getter and Setters
     */

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getFontFamilyId() {
        return fontFamilyId;
    }

    public void setFontFamilyId(int fontFamilyId) {
        this.fontFamilyId = fontFamilyId;
    }

    public ColorStateList getTextColor() {
        return textColor;
    }

    public void setTextColor(ColorStateList textColor) {
        this.textColor = textColor;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
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

    public void setCurveDepth(int curveDepth) {
        this.curveDepth = curveDepth;
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public int getNavigationBackgroundColor() {
        return navigationBackgroundColor;
    }

    public void setNavigationBackgroundColor(int navigationBackgroundColor) {
        this.navigationBackgroundColor = navigationBackgroundColor;
    }

    public int getContainerBackgroundColor() {
        return containerBackgroundColor;
    }

    public void setContainerBackgroundColor(int containerBackgroundColor) {
        this.containerBackgroundColor = containerBackgroundColor;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public OnItemSelectedListener getOnItemSelectedListener() {
        return onItemSelectedListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public FrameLayout getFrameLayout() {
        return layout;
    }

    /**
     * Public interface
     */
    public interface OnItemSelectedListener{
        void onSelect(int itemId);
    }
}
