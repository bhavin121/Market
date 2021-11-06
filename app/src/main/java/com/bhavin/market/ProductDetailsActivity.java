package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.bhavin.market.adapters.SliderAdapter;
import com.bhavin.market.databinding.ActivityProductDetailsBinding;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    public static final String PRODUCT_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProductDetailsBinding binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Helper.makeActivityFullscreen(this);

        String id = getIntent().getStringExtra(PRODUCT_ID);
        if(id==null){
            Toast.makeText(this , "Something went wrong" , Toast.LENGTH_SHORT).show();
            return;
        }else{
            fetchProductDetails();
        }

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

    private void fetchProductDetails(){

    }
}