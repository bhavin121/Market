package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Pair;
import android.widget.Toast;

import com.bhavin.market.adapters.ProductsAndDetailsAdapter;
import com.bhavin.market.classes.Seller;
import com.bhavin.market.databinding.ActivitySellerDetailsBinding;
import com.bhavin.market.viewModels.SellerDetailsViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.GsonBuilder;

public class SellerDetailsActivity extends AppCompatActivity {

    private ActivitySellerDetailsBinding binding;
    private SellerDetailsViewModel viewModel;
    public static final String DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SellerDetailsViewModel.class);

        String json = getIntent().getStringExtra(DATA);
        if(json != null) viewModel.setSeller(new GsonBuilder().create().fromJson(json, Seller.class));

        Helper.makeActivityFullscreen(this);

        Seller seller = viewModel.getSeller();
        binding.name.setText(seller.getShopName());
        binding.shopName.setText(seller.getShopName());
        binding.rating.setText(seller.getAvgRating());
        binding.type.setText(seller.getCategory());
        Glide.with(this)
                .load(seller.getBannerUrl())
                .centerCrop()
                .into(binding.banner);

        String[] tabNames = {"Products", "Details"};
        binding.pager.setAdapter(new ProductsAndDetailsAdapter(getSupportFragmentManager(), getLifecycle()));
        new TabLayoutMediator(binding.tabLayout, binding.pager, (tab, position) -> tab.setText(tabNames[position])).attach();

        binding.backButton.setOnClickListener(view -> onBackPressed());
    }
}