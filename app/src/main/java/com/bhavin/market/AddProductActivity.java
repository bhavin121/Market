package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import com.bhavin.market.databinding.ActivityAddProductBinding;

public class AddProductActivity extends AppCompatActivity {

    public ActivityAddProductBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Helper.makeActivityFullscreen(this);

        NavHostFragment fragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHost1);
        navController = fragment != null ? fragment.getNavController() : null;
    }
}