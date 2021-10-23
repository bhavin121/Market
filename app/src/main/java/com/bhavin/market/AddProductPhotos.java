package com.bhavin.market;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhavin.market.adapters.ImageItemAdapter;
import com.bhavin.market.databinding.FragmentAddProductPhotosBinding;
import com.bhavin.market.viewModels.AddProductViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AddProductPhotos extends Fragment {

    private FragmentAddProductPhotosBinding binding;
    private ActivityResultLauncher<Intent> launcher;
    private AddProductViewModel viewModel;
    private ImageItemAdapter adapter;

    public AddProductPhotos() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context){
        super.onAttach(context);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                if(viewModel.getUriList().size() == 0) binding.noImageChosen.setVisibility(View.GONE);
                viewModel.addImage(result.getData().getData());
                adapter.notifyItemInserted(viewModel.getUriList().size()-1);
            }
        });
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddProductPhotosBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AddProductViewModel.class);

        adapter = new ImageItemAdapter(viewModel.getUriList() , ( ) -> binding.noImageChosen.setVisibility(View.VISIBLE));
        binding.photosList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.photosList.setAdapter(adapter);

        binding.pickImageButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            launcher.launch(intent);
        });

        binding.next.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_addProductPhotos_to_addProductFragment);
        });
    }
}