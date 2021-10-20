package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;

public class SellerRegistrationActivity extends AppCompatActivity implements SellerRegistrationFragment.Listener {

    NavController navController;
    public static final String RESULT = "res";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);

        Helper.makeActivityFullscreen(this);

        NavHostFragment fragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHost1);
        navController = fragment != null ? fragment.getNavController() : null;
    }

    @Override
    public void onRegistrationSuccess(){
        setResult(RESULT_OK, new Intent().putExtra(RESULT, true));
        finish();
    }
}