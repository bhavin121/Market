package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.os.Bundle;

import com.bhavin.market.adapters.PlaceOrderAdapter;
import com.bhavin.market.databinding.ActivityPlaceOrderBinding;

public class PlaceOrderActivity extends AppCompatActivity {

    ActivityPlaceOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityPlaceOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(Color.WHITE);

        PlaceOrderAdapter adapter = new PlaceOrderAdapter(new PlaceOrderAdapter.ClickListener() {
            @Override
            public void changeQuantity(String productId , String newQuantity){
                
            }

            @Override
            public void deleteItem(String productId){

            }

            @Override
            public void showProductDetails(String productId){

            }

            @Override
            public void payNow( ){

            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }
}