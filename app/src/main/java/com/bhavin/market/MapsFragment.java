package com.bhavin.market;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhavin.market.databinding.FragmentMapsBinding;
import com.bhavin.market.viewModels.SellerRegistrationViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment implements OnMapReadyCallback{

    private SellerRegistrationViewModel viewModel;
    private FragmentMapsBinding binding;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

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
            mapFragment.getMapAsync(this);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        viewModel = new ViewModelProvider(requireActivity()).get(SellerRegistrationViewModel.class);

        binding.confirmButton.setOnClickListener(view1 -> {
            // Navigate to seller registration fragment
            Navigation.findNavController(view1).navigate(R.id.action_mapsFragment_to_sellerRegistrationFragment);
        });
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap){
        mMap = googleMap;

        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity() , new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location){
                            if(location != null){
                                LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(latLng));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            }
                        }
                    });
        }else{
            LatLng latLng = new LatLng(28.38 , 77.12);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f));

        mMap.setOnMapClickListener(latLng1 -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng1));
            viewModel.setCurrentLatLng(latLng1);
        });
    }
}