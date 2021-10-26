package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.bhavin.market.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng currentLatLng;
    public static final String RESULT = "res";
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        com.bhavin.market.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Helper.makeActivityFullscreen(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        binding.confirmButton.setOnClickListener(view -> getAddressFromLatlng(currentLatLng));
    }

    private void getAddressFromLatlng(LatLng currentLatLng){
        Geocoder geocoder = new Geocoder(this , Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(currentLatLng.latitude , currentLatLng.longitude , 1);
            Address address = addresses.get(0);

            com.bhavin.market.classes.Address addressRes = new com.bhavin.market.classes.Address();
            addressRes.setCountry(address.getCountryName());
            addressRes.setState(address.getAdminArea());
            addressRes.setCity(address.getLocality());
            addressRes.setStreetLane(address.getSubLocality());
            addressRes.setPincode(address.getPostalCode());
            addressRes.setLongtitude(String.valueOf(currentLatLng.longitude));
            addressRes.setLatitude(String.valueOf(currentLatLng.latitude));

            String res = new GsonBuilder().create().toJson(addressRes);
            Intent intent = new Intent();
            intent.putExtra(RESULT , res);
            setResult(RESULT_OK , intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this , "Something went wrong" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NotNull GoogleMap googleMap){
        mMap = googleMap;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this , new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location){
                            if(location != null){
                                LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
                                currentLatLng = latLng;
                                mMap.addMarker(new MarkerOptions().position(latLng));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            }
                        }
                    });
        }else{
            LatLng latLng = new LatLng(28.38 , 77.12);
            currentLatLng = latLng;
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f));

        mMap.setOnMapClickListener(latLng1 -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng1));
            currentLatLng = latLng1;
        });
    }
}