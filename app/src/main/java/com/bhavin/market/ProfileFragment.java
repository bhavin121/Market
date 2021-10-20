package com.bhavin.market;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhavin.market.databinding.FragmentProfileBinding;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public ProfileFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view , @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view , savedInstanceState);

        binding.name.setText(MessageFormat.format("{0} {1}" , Helper.user.getFirstName() , Helper.user.getLastName()));

        binding.faq.setOnClickListener(view1 -> {
            startActivity(new Intent(requireContext(), FAQActivity.class));
        });

        binding.pastOrders.setOnClickListener(view1 -> {
            startActivity(new Intent(requireContext(), MyOrdersActivity.class));
        });

        binding.logOut.setOnClickListener(view1 -> {

        });
    }
}