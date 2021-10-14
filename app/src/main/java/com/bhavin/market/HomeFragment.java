package com.bhavin.market;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhavin.market.adapters.HomeAdapter;
import com.bhavin.market.databinding.AddressDialogBinding;
import com.bhavin.market.databinding.FragmentHomeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private BottomSheetDialog addressDialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view , @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view , savedInstanceState);

        buildAddressDialog();

        List<String> items = Arrays.asList("Fist","Second","Third", "Fourth");
        HomeAdapter adapter = new HomeAdapter(items);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.addressButton.setOnClickListener(view1 -> {
            addressDialog.show();
        });
    }

    private void buildAddressDialog(){
        AddressDialogBinding binding = AddressDialogBinding.inflate(LayoutInflater.from(requireContext()));
        addressDialog = new BottomSheetDialog(requireContext());
        addressDialog.setContentView(binding.getRoot());

        binding.addNewAddress.setOnClickListener(view -> {

        });

        binding.saveChanges.setOnClickListener(view -> {

        });
    }
}