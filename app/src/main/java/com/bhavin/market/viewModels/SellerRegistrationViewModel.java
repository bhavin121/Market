package com.bhavin.market.viewModels;

import android.app.Application;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Pair;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.bhavin.market.classes.Seller;
import com.bhavin.market.classes.SellerData;
import com.bhavin.market.model.SellerRegistrationRepository;
import com.google.android.gms.maps.model.LatLng;
import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SellerRegistrationViewModel extends AndroidViewModel {

    private LatLng currentLatLng;
    private final SellerRegistrationRepository repository;

    public SellerRegistrationViewModel(@NonNull @NotNull Application application){
        super(application);
        repository = new SellerRegistrationRepository(application);
    }

    public void setCurrentLatLng(LatLng currentLatLng){
        this.currentLatLng = currentLatLng;
    }

    public LatLng getCurrentLatLng( ){
        return currentLatLng;
    }

    public com.bhavin.market.classes.Address getAddress(){
        if(currentLatLng == null) return null;

        com.bhavin.market.classes.Address addressRes = new com.bhavin.market.classes.Address();
        Geocoder geocoder = new Geocoder(getApplication(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(currentLatLng.latitude , currentLatLng.longitude , 1);
            Address address = addresses.get(0);
            addressRes.setCountry(address.getCountryName());
            addressRes.setState(address.getAdminArea());
            addressRes.setCity(address.getLocality());
            addressRes.setStreetLane(address.getSubLocality());
            addressRes.setPincode(address.getPostalCode());
            return addressRes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MutableLiveData<Pair<Boolean,SellerData>> registerSeller(Seller seller , com.bhavin.market.classes.Address address, Uri photo){
        MutableLiveData<Pair<Boolean,SellerData>> res = new MutableLiveData<>(new Pair<>(false, null));

        address.setLatitude(String.valueOf(currentLatLng.latitude));
        address.setLongtitude(String.valueOf(currentLatLng.longitude));
        repository.uploadImage(photo).observe(getApplication() , uploadResult -> {
            if(uploadResult.wasUploading){
                if(uploadResult.uploadSuccess){
                    seller.setBannerUrl(uploadResult.photoUrl.toString());
                    seller.setRegistrationDate(getDateAndTime());
                    repository.registerSeller(seller, address).observe(
                            getApplication() ,
                            res::postValue
                    );
                }else{
                    Toast.makeText(getApplication() , "Something went wrong" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        return res;
    }

    private String getDateAndTime( ){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
