package com.bhavin.market;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhavin.market.databinding.FragmentSellerPanelBinding;

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
}