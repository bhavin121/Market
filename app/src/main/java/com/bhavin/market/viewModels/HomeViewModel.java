package com.bhavin.market.viewModels;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bhavin.market.Helper;
import com.bhavin.market.classes.Address;
import com.bhavin.market.model.CommonRepository;

import org.jetbrains.annotations.NotNull;

public class HomeViewModel extends AndroidViewModel {

    private final CommonRepository repository;

    public HomeViewModel(@NonNull @NotNull Application application){
        super(application);
        repository = new CommonRepository(application);
    }

    public MutableLiveData<Pair<Boolean,Address>> addNewAddress(Address address){
        address.setEmail(Helper.user.getEmail());
        return repository.addNewAddress(address);
    }
}
