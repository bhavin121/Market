package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.bhavin.market.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity implements NoSellerFragment.Listener {

    @SuppressLint ("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = ActivityHomeBinding.inflate ( getLayoutInflater () );
        setContentView( binding.getRoot());

//        getWindow().setStatusBarColor(Color.WHITE);

        changeFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    changeFragment(new HomeFragment());
                    break;
                case R.id.search:
                    changeFragment(new SearchFragment());
                    break;
                case R.id.cart:
                    changeFragment(new ShoppingCartFragment());
                    break;
                case R.id.sell:
                    if(Helper.user != null){
                        changeFragment((Helper.user.isSeller())?new SellerPanelFragment(): new NoSellerFragment());
                    }else{
                        Toast.makeText(this , "There is some problem" , Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.profile:
                    changeFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    public void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();
    }

    @Override
    public void onRegistrationSuccess(){
        changeFragment(new SellerPanelFragment());
    }
}