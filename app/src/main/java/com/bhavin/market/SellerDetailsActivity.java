package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.bhavin.market.adapters.ProductsAndDetailsAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SellerDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_details);

        Helper.makeActivityFullscreen(this);

        String[] tabNames = {"Products", "Details"};

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = findViewById(R.id.pager);
        viewPager2.setAdapter(new ProductsAndDetailsAdapter(getSupportFragmentManager(), getLifecycle()));
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(tabNames[position]);
        }).attach();
    }
}