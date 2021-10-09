package com.bhavin.market;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhavin.market.adapters.ShopListAdapter;
import com.bhavin.market.databinding.FragmentShopsListBinding;

import org.jetbrains.annotations.NotNull;

public class ShopsListFragment extends Fragment {

    private FragmentShopsListBinding binding;

    public ShopsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShopsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.shopList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.shopList.setAdapter(new ShopListAdapter());
    }
}