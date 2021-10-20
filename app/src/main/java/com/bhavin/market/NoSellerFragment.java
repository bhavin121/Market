package com.bhavin.market;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhavin.market.databinding.FragmentNoSellerBinding;

import org.jetbrains.annotations.NotNull;

public class NoSellerFragment extends Fragment {

    private FragmentNoSellerBinding binding;

    public NoSellerFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        binding = FragmentNoSellerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view , @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view , savedInstanceState);

        binding.registerButton.setOnClickListener(view1 -> {
            startActivity(new Intent(requireContext(), SellerRegistrationActivity.class));
        });
    }
}