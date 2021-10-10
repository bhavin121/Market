package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bhavin.market.adapters.SliderAdapter;
import com.bhavin.market.databinding.ActivityProductDetailsBinding;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProductDetailsBinding binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Helper.makeActivityFullscreen(this);

        SliderAdapter adapter = new SliderAdapter(this);
        List<String> items = new ArrayList <>();
        items.add(String.valueOf(R.drawable.clothes_bag));
        items.add(String.valueOf(R.drawable.food_bag));
        items.add(String.valueOf(R.drawable.shoe_bag));
        items.add(String.valueOf(R.drawable.fruits_bag));
        adapter.setItems(items);

        binding.slider.setSliderAdapter(adapter);
        binding.slider.setIndicatorAnimation(IndicatorAnimationType.SCALE);
        binding.slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
    }
}