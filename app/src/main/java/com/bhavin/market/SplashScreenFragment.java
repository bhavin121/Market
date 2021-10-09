package com.bhavin.market;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

public class SplashScreenFragment extends Fragment {

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scheduleSplash(view.findViewById(R.id.logo));
    }

    private void scheduleSplash(View view) {
        NavController navController = Navigation.findNavController(view);
        new Handler().postDelayed(() -> {
            FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder().
                    addSharedElement(view, "logo")
                    .build();
            navController.navigate(R.id.action_splashScreenFragment_to_logInFragment
                                    ,null, null, extras);
        }, 3000);
    }
}