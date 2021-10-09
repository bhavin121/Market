package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import android.os.Bundle;
import android.os.Handler;

import com.bhavin.market.databinding.FragmentOTPAuthBinding;
import com.bhavin.market.databinding.ProductCardBinding;

public class MainActivity extends AppCompatActivity{

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Helper.makeActivityFullscreen(this);

        NavHostFragment fragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHost);
        navController = fragment != null ? fragment.getNavController() : null;

    }
}