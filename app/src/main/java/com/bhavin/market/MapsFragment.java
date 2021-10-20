package com.bhavin.market;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhavin.market.databinding.FragmentMapsBinding;
import com.bhavin.market.viewModels.SellerRegistrationViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment {

    private SellerRegistrationViewModel viewModel;
    private FragmentMapsBinding binding;
    private GoogleMap mMap;

    private final OnMapReadyCallback callback = googleMap -> {
        mMap = googleMap;

        LatLng latLng = new LatLng(28.38 , 77.12);
        viewModel.setCurrentLatLng(latLng);

        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f));

        mMap.setOnMapClickListener(latLng1 -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng1));
            viewModel.setCurrentLatLng(latLng1);
        });
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater ,
                             @Nullable ViewGroup container ,
                             @Nullable Bundle savedInstanceState){
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState){
        super.onViewCreated(view , savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment!=null) {
            mapFragment.getMapAsync(callback);
        }

        viewModel = new ViewModelProvider(requireActivity()).get(SellerRegistrationViewModel.class);

        binding.confirmButton.setOnClickListener(view1 -> {
            // Navigate to seller registration fragment
            Navigation.findNavController(view1).navigate(R.id.action_mapsFragment_to_sellerRegistrationFragment);
        });
    }
}