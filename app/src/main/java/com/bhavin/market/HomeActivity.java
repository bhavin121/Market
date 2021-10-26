package com.bhavin.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.bhavin.market.databinding.ActivityHomeBinding;
import com.bhavin.market.databinding.LocationNoticeDialogBinding;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private LocationNoticeDialogBinding binding;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private ShoppingCartFragment shoppingCartFragment;
    private SellerPanelFragment sellerPanelFragment;
    private ProfileFragment profileFragment;
    private NoSellerFragment noSellerFragment;

    @SuppressLint ("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getWindow().setStatusBarColor(Color.WHITE);
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        shoppingCartFragment = new ShoppingCartFragment();
        sellerPanelFragment = new SellerPanelFragment();
        profileFragment = new ProfileFragment();
        noSellerFragment = new NoSellerFragment();

        profileFragment.setListener(this::finish);

        changeFragment(homeFragment);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    changeFragment(homeFragment);
                    break;
                case R.id.search:
                    changeFragment(searchFragment);
                    break;
                case R.id.cart:
                    changeFragment(shoppingCartFragment);
                    break;
                case R.id.sell:
                    if(Helper.user!=null) {
                        changeFragment((Helper.user.isSeller()) ? sellerPanelFragment :noSellerFragment.setListener(( ) -> changeFragment(sellerPanelFragment)));
                    } else {
                        Toast.makeText(this , "There is some problem" , Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.profile:
                    changeFragment(profileFragment);
                    break;
            }
            return true;
        });

        buildLocationNoticeDialog();
        getCurrentLocation();
    }

    private void buildLocationNoticeDialog( ){
        binding = LocationNoticeDialogBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(binding.getRoot());
        dialog = builder.create();

        binding.yes.setOnClickListener(view -> {
            dialog.dismiss();
            String city = binding.city.getText().toString();
            if(!homeFragment.isDetached()){
                homeFragment.fetchSellers(city);
            }else{
                changeFragment(homeFragment);
                homeFragment.fetchSellers(city);
            }
        });

        binding.no.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

    private void getCurrentLocation( ){
        if(ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            retrieveLocation();
        } else {
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 100);
        }
    }

    @SuppressLint ("MissingPermission")
    private void retrieveLocation(){
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 10000 , 10 , new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location){

            }

            @Override
            public void onProviderDisabled(@NonNull String provider){

            }

            @Override
            public void onProviderEnabled(@NonNull String provider){

            }

            @Override
            public void onStatusChanged(String provider , int status , Bundle extras){

            }
        });

        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location != null){
            Geocoder geocoder = new Geocoder(this);
            try {
                List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Address address = addressList.get(0);
                String city = address.getLocality();
                if(Helper.user.getCurrentAddressObj() == null) return;
                if(!Helper.user.getCurrentAddressObj().getCity().equals(city)){
                    System.out.println(city);
                    binding.city.setText(city);

                    dialog.show();
                }else{
                    System.out.println("Same");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode , @NonNull @NotNull String[] permissions , @NonNull @NotNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode , permissions , grantResults);

        if(requestCode == 100 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            retrieveLocation();
        }
    }
}