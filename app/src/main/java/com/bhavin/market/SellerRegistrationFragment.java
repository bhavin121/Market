package com.bhavin.market;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.bhavin.market.classes.Address;
import com.bhavin.market.classes.Seller;
import com.bhavin.market.classes.User;
import com.bhavin.market.databinding.FragmentSellerRegistrationBinding;
import com.bhavin.market.viewModels.SellerRegistrationViewModel;
import org.jetbrains.annotations.NotNull;

public class SellerRegistrationFragment extends Fragment {

    private FragmentSellerRegistrationBinding binding;
    private SellerRegistrationViewModel viewModel;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri photo;
    private ArrayAdapter<CharSequence> adapter;

    public SellerRegistrationFragment(){
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context){
        super.onAttach(context);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult() ,
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        photo = result.getData()!=null ? result.getData().getData() : null;
                        if(photo != null){
                            binding.banner.setImageURI(photo);
                        }
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState){
        binding = FragmentSellerRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view , @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view , savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SellerRegistrationViewModel.class);

        User user = Helper.user;
        if(user == null){
            Toast.makeText(requireContext() , "There is some problem" , Toast.LENGTH_SHORT).show();
            return;
        }
        Address address = viewModel.getAddress();
        binding.city.setText(address.getCity());
        binding.country.setText(address.getCountry());
        binding.pinCode.setText(address.getPincode());
        binding.state.setText(address.getState());
        binding.street.setText(address.getStreetLane());
        binding.contactNo.setText(user.getContactNo());

        binding.pickImageButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            activityResultLauncher.launch(intent);
        });

        binding.registerButton.setOnClickListener(view1 -> registerSeller());

        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categories.setAdapter(adapter);
    }

    private void registerSeller(){
        User user = Helper.user;
        if(user == null){
            Toast.makeText(requireContext() , "There is some problem" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(photo != null){
            // Extract data from all edit texts
            Address address = new Address();
            address.setPincode(binding.pinCode.getText().toString());
            address.setCity(binding.city.getText().toString());
            address.setState(binding.state.getText().toString());
            address.setCountry(binding.country.getText().toString());
            address.setStreetLane(binding.street.getText().toString());
            address.setSellerFlag(true);
            address.setEmail(user.getEmail());
            address.setPhoneNo(binding.contactNo.getText().toString());

            Seller seller = new Seller();
            seller.setGstNo(binding.gstNo.getText().toString());
            seller.setShopName(binding.shopName.getText().toString());
            seller.setTimingStart(binding.openingTime.getText().toString());
            seller.setTimingEnd(binding.closingTime.getText().toString());
            seller.setCategory(adapter.getItem(binding.categories.getSelectedItemPosition()).toString());

            if(anyFieldEmpty(address, seller)){
                return;
            }

            // Start registration
            viewModel.registerSeller(seller, address, photo);
        }else{
            Toast.makeText(requireContext() , "Please pick a photo" , Toast.LENGTH_SHORT).show();
        }
    }

    private boolean anyFieldEmpty(Address address, Seller seller){
        boolean flag = false;

        if(address.getCity().isEmpty() || address.getCountry().isEmpty()
                || address.getPhoneNo().isEmpty() || address.getPincode().isEmpty()
                || address.getState().isEmpty() || address.getStreetLane().isEmpty()
                || seller.getShopName().isEmpty() || seller.getGstNo().isEmpty()
                || seller.getTimingStart().isEmpty() || seller.getTimingEnd().isEmpty()){
            flag = true;
            Toast.makeText(requireContext() , "Please fill empty fields" , Toast.LENGTH_SHORT).show();
        }

        return flag;
    }
}