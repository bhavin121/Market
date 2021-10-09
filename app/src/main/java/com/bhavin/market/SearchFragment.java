package com.bhavin.market;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhavin.market.adapters.ShopProductAdapter;
import com.bhavin.market.databinding.FragmentSearchBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] tabNames = {"Shops", "Products"};
        binding.pager.setAdapter(new ShopProductAdapter(getFragmentManager(), getLifecycle()));
        new TabLayoutMediator(binding.tabLayout, binding.pager, (tab, position) -> {
            tab.setText(tabNames[position]);
        }).attach();

        binding.searchButton.setOnClickListener(view1 -> {
            binding.searching.setVisibility(View.VISIBLE);
            binding.searching.playAnimation();
            new Handler().postDelayed(() -> {
                binding.searching.pauseAnimation();
                binding.searching.setVisibility(View.GONE);
            }, 5000);
        });
    }
}