package com.bhavin.market;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;
import com.bhavin.market.adapters.AddressAdapter;
import com.bhavin.market.adapters.HomeAdapter;
import com.bhavin.market.classes.Address;
import com.bhavin.market.classes.Seller;
import com.bhavin.market.customViews.LoadingDialogBuilder;
import com.bhavin.market.databinding.AddAddressDialogBinding;
import com.bhavin.market.databinding.AddressDialogBinding;
import com.bhavin.market.databinding.FragmentHomeBinding;
import com.bhavin.market.viewModels.HomeViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private BottomSheetDialog addressDialog, addAddressDialog;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Address address;
    private AddAddressDialogBinding addressBinding;
    private HomeViewModel viewModel;
    private AddressAdapter adapter;
    private HomeAdapter homeAdapter;
    private AlertDialog loading;
    private boolean isScrolling = false;
    private boolean isFetchingSellers = false;

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
        loading = LoadingDialogBuilder.build(requireContext());

        buildAddressDialog();
        buildAddAddressDialog();

        homeAdapter = new HomeAdapter(viewModel.getSellersDataList().getData() ,
                new HomeAdapter.OnItemClickListener() {
                    @Override
                    public void onSellerClick(Seller seller){
                        System.out.println(seller.toString());
                        String data = new GsonBuilder().create().toJson(seller);
                        Intent intent = new Intent(requireContext(), SellerDetailsActivity.class);
                        intent.putExtra(SellerDetailsActivity.DATA, data);
                        startActivity(intent);
                    }

                    @Override
                    public void onCategoriesClick(String categories){

                    }
                });

        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(homeAdapter);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView , int newState){
                super.onScrollStateChanged(recyclerView , newState);
                if(newState ==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView , int dx , int dy){
                super.onScrolled(recyclerView , dx , dy);
                int visibleItems = manager.getChildCount();
                int totalItems = manager.getItemCount();
                int scrolledOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && (scrolledOutItems+visibleItems == totalItems)){
                    isScrolling = false;
                    fetchSellers(viewModel.getCity());
                }
            }
        });

        if(Helper.user.getCurrentAddressObj() != null){
            fetchSellers(Helper.user.getCurrentAddressObj().getCity());
            binding.address.setText(MessageFormat.format("{0},{1}" , Helper.user.getCurrentAddressObj().getStreetLane() , Helper.user.getCurrentAddressObj().getCity()));
        }
        binding.addressButton.setOnClickListener(view1 -> addressDialog.show());
    }

    public void fetchSellers(String city){
        if(isFetchingSellers) return;

//        binding.progressBar.setVisibility(View.VISIBLE);
//        binding.noMoreSeller.setVisibility(View.GONE);
        homeAdapter.setNoMoreData(false);
        isFetchingSellers = true;
        viewModel.fetchSellersInCity(requireActivity(), city)
                .observe(requireActivity() , integer -> {
                    if(integer == null) return;
                    isFetchingSellers = false;
//                    binding.progressBar.setVisibility(View.GONE);
                    switch (integer){
                        case HomeViewModel.NEW_DATA_SET_ADDED:
                            homeAdapter.notifyDataSetChanged();
                            homeAdapter.setNoMoreData(false);
                            break;
                        case HomeViewModel.NO_MORE_DATA:
//                            binding.noMoreSeller.setVisibility(View.VISIBLE);
                            homeAdapter.setNoMoreData(true);
                            break;
                        case HomeViewModel.NO_SELLER_IN_CITY:
                            break;
                        case HomeViewModel.MORE_DATA_ADDED:
                            int count = viewModel.getSellersDataList().getSize() - viewModel.getSellersDataList().getLastSize();
                            homeAdapter.notifyItemRangeInserted(viewModel.getSellersDataList().getLastSize()+2, count);
                            break;
                        case HomeViewModel.DATA_FETCH_FAILED:
                            Toast.makeText(requireContext() , "Something went wrong" , Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
    }

    private void buildAddAddressDialog(){
        addressBinding = AddAddressDialogBinding.inflate(LayoutInflater.from(requireContext()));
        addAddressDialog = new BottomSheetDialog(requireContext(), R.style.SheetDialog);
        addAddressDialog.setContentView(addressBinding.getRoot());

        addressBinding.addAddress.setOnClickListener(view -> {
            addAddressDialog.dismiss();
            address.setPincode(addressBinding.pinCode.getText().toString());
            address.setCity(addressBinding.city.getText().toString());
            address.setState(addressBinding.state.getText().toString());
            address.setCountry(addressBinding.country.getText().toString());
            address.setStreetLane(addressBinding.street.getText().toString());
            address.setPhoneNo(addressBinding.contactNo.getText().toString());

            // show loading dialog
            loading.show();
            viewModel.addNewAddress(address).observe(requireActivity() , booleanAddressPair -> {
                if(booleanAddressPair.first){
                    loading.dismiss();
                    if(booleanAddressPair.second == null){
                        // Error occurred
                        Toast.makeText(requireContext() , "Adding address failed" , Toast.LENGTH_SHORT).show();
                    }else{
                        Helper.user.setCurrAddress(booleanAddressPair.second.getAddressId());
                        if(Helper.user.getAddress() == null){
                            Helper.user.setAddress(Collections.singletonList(booleanAddressPair.second));
                            adapter.notifyItemInserted(0);
                        }else{
                            Helper.user.getAddress().add(booleanAddressPair.second);
                            adapter.notifyItemInserted(Helper.user.getAddress().size()-1);
                        }
                        binding.address.setText(MessageFormat.format("{0},{1}" , booleanAddressPair.second.getStreetLane() , booleanAddressPair.second.getCity()));
                        adapter.swap(booleanAddressPair.second.getAddressId());
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

        adapter = new AddressAdapter(Helper.user.getAddress());

        binding.list.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.list.setAdapter(adapter);

        binding.saveChanges.setOnClickListener(view -> {
            loading.show();
            viewModel.changeCurrentAddress(adapter.getChosenAddressId())
                    .observe(requireActivity() , booleanStringPair -> {
                        if(booleanStringPair.first){
                            loading.dismiss();
                            adapter.swap(booleanStringPair.second);
                            this.binding.address.setText(MessageFormat.format("{0},{1}" , Helper.user.getCurrentAddressObj().getStreetLane() , Helper.user.getCurrentAddressObj().getCity()));
                        }
                    });
        });
    }
}