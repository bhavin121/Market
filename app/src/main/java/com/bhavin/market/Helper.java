package com.bhavin.market;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bhavin.market.classes.User;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class Helper {

    public static User user;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void configSourceSharedTransitions(AppCompatActivity activity){
//        activity.getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        activity.setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        activity.getWindow().setSharedElementsUseOverlay(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void configTargetSharedTransitions(AppCompatActivity activity, int targetId, String transitionName){

//        activity.getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

        activity.findViewById(targetId).setTransitionName(transitionName);
        activity.setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());

        MaterialContainerTransform transform = new MaterialContainerTransform();
        transform.addTarget(targetId);
        transform.setDuration(500);

        activity.getWindow().setSharedElementEnterTransition(transform);
        activity.getWindow().setSharedElementReturnTransition(transform);
    }

    public static void makeActivityFullscreen(AppCompatActivity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public interface DataLoadingListener<T>{
        void onSuccess(T t);
        void onError();
    }
}
