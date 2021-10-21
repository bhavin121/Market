package com.bhavin.market;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhavin.market.adapters.ColorAdapter;
import com.bhavin.market.adapters.SizeAdapter;
import com.bhavin.market.classes.Color;
import com.bhavin.market.databinding.ColorDialogBinding;
import com.bhavin.market.databinding.FragmentAddProductBinding;
import com.bhavin.market.databinding.SizeDialogBinding;
import com.bhavin.market.viewModels.AddProductViewModel;

import org.jetbrains.annotations.NotNull;

public class AddProductFragment extends Fragment {

    public FragmentAddProductBinding binding;
    private AlertDialog colorPickerDialog, sizeDialog;
    private AddProductViewModel viewModel;
    private ColorAdapter colorAdapter;
    private SizeAdapter sizeAdapter;

    public AddProductFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view , @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view , savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AddProductViewModel.class);
        colorAdapter = new ColorAdapter(viewModel.getColors());
        binding.colorsList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.colorsList.setAdapter(colorAdapter);

        sizeAdapter = new SizeAdapter(viewModel.getSizes());
        binding.sizeList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.sizeList.setAdapter(sizeAdapter);

        buildColorPickerDialog();
        buildSizeDialog();

        binding.addColor.setOnClickListener(view1 -> colorPickerDialog.show());
        binding.addSize.setOnClickListener(view1 -> sizeDialog.show());
    }

    private void buildSizeDialog( ){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        SizeDialogBinding binding = SizeDialogBinding.inflate(LayoutInflater.from(requireContext()));

        builder.setTitle("Add Size")
                .setPositiveButton("Ok" , (dialogInterface , i) -> {
                    viewModel.addSize(binding.text.getText().toString());
                    sizeAdapter.notifyChange();
                })
                .setNegativeButton("Cancel", null)
                .setView(binding.getRoot());

        sizeDialog = builder.create();
    }

    private void buildColorPickerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        ColorDialogBinding binding = ColorDialogBinding.inflate(LayoutInflater.from(requireContext()));
        builder.setTitle("Add Color")
                .setPositiveButton("Ok" , (dialogInterface , i) -> {
                    Color color = new Color(binding.color.getText().toString(), binding.name.getText().toString());
                    viewModel.addColor(color);
                    colorAdapter.notifyChange();
                })
                .setNegativeButton("Cancel", null)
        .setView(binding.getRoot());

        binding.colorImage.setImageTintList(ColorStateList.valueOf(android.graphics.Color.WHITE));
        binding.colorPickerView.setColor(android.graphics.Color.WHITE);

        binding.colorPickerView.setColorListener((integer , s) -> {
            binding.color.setText(s);
            binding.colorImage.setImageTintList(ColorStateList.valueOf(integer));
            return null;
        });

        colorPickerDialog = builder.create();
    }
}