package com.bhavin.market;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhavin.market.databinding.FragmentSellerPanelBinding;

import org.jetbrains.annotations.NotNull;

public class SellerPanelFragment extends Fragment {

    private FragmentSellerPanelBinding binding;

    public SellerPanelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSellerPanelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view , @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view , savedInstanceState);
        binding.addProduct.setOnClickListener(view1 -> {
            startActivity(new Intent(requireContext(), AddProductActivity.class));
        });
    }
}