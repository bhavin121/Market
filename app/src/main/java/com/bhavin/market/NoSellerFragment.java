package com.bhavin.market;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    private Listener listener;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    public NoSellerFragment(){
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context){
        super.onAttach(context);
        if(context instanceof SellerRegistrationFragment.Listener){
            listener = (Listener) context;
        }else{
            System.err.println(context.getClass() + " must implement SellerRegistrationFragment.Listener");
        }
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult() ,
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        if(result.getData()!=null && result.getData().getBooleanExtra(SellerRegistrationActivity.RESULT , false)) {
                            listener.onRegistrationSuccess();
                        }
                    }
                });
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
            activityResultLauncher.launch(new Intent(requireContext(), SellerRegistrationActivity.class));
        });
    }

    public interface Listener{
        void onRegistrationSuccess();
    }
}