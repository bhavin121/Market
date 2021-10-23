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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bhavin.market.adapters.AddressAdapter;
import com.bhavin.market.adapters.HomeAdapter;
import com.bhavin.market.classes.Address;
import com.bhavin.market.databinding.AddAddressDialogBinding;
import com.bhavin.market.databinding.AddressDialogBinding;
import com.bhavin.market.databinding.FragmentHomeBinding;
import com.bhavin.market.viewModels.HomeViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private BottomSheetDialog addressDialog, addAddressDialog;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Address address;
    private AddAddressDialogBinding addressBinding;
    private HomeViewModel viewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context){
        super.onAttach(context);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult() ,
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        if(result.getData() != null) {
                            String res = result.getData().getStringExtra(MapsActivity.RESULT);
                            address = new GsonBuilder().create().fromJson(res, Address.class);
                            showAddAddressDialog();
                        }
                    }
                });
    }

    private void showAddAddressDialog(){
        addressBinding.city.setText(address.getCity());
        addressBinding.contactNo.setText(Helper.user.getContactNo());
        addressBinding.country.setText(address.getCountry());
        addressBinding.pinCode.setText(address.getPincode());
        addressBinding.state.setText(address.getState());
        addressBinding.street.setText(address.getStreetLane());

        addAddressDialog.show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot(); 
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view , @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view , savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        buildAddressDialog();
        buildAddAddressDialog();

        List<String> items = Arrays.asList("Fist","Second","Third", "Fourth");
        HomeAdapter adapter = new HomeAdapter(items);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.addressButton.setOnClickListener(view1 -> addressDialog.show());
    }

    private void buildAddAddressDialog(){
        addressBinding = AddAddressDialogBinding.inflate(LayoutInflater.from(requireContext()));
        addAddressDialog = new BottomSheetDialog(requireContext(), R.style.SheetDialog);
        addAddressDialog.setContentView(addressBinding.getRoot());

        addressBinding.registerButton.setOnClickListener(view -> {
            addAddressDialog.dismiss();
            address.setPincode(addressBinding.pinCode.getText().toString());
            address.setCity(addressBinding.city.getText().toString());
            address.setState(addressBinding.state.getText().toString());
            address.setCountry(addressBinding.country.getText().toString());
            address.setStreetLane(addressBinding.street.getText().toString());
            address.setPhoneNo(addressBinding.contactNo.getText().toString());
            viewModel.addNewAddress(address).observe(requireActivity() , booleanAddressPair -> {
                if(booleanAddressPair.first){
                    if(booleanAddressPair.second == null){
                        // Error occurred
                        Toast.makeText(requireContext() , "Adding address failed" , Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println(booleanAddressPair.second.toString(true));
                    }
                }
            });
        });
    }

    private void buildAddressDialog(){
        AddressDialogBinding binding = AddressDialogBinding.inflate(LayoutInflater.from(requireContext()));
        addressDialog = new BottomSheetDialog(requireContext(), R.style.SheetDialog);
        addressDialog.setContentView(binding.getRoot());

        binding.addNewAddress.setOnClickListener(view -> {
            addressDialog.dismiss();
            activityResultLauncher.launch(new Intent(requireContext(), MapsActivity.class));
        });

        binding.saveChanges.setOnClickListener(view -> {

        });

        binding.list.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.list.setAdapter(new AddressAdapter(null));
    }
}