package com.bhavin.market.viewModels;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bhavin.market.Helper;
import com.bhavin.market.adapters.HomeAdapter;
import com.bhavin.market.classes.Address;
import com.bhavin.market.classes.SellersDataList;
import com.bhavin.market.model.AuthRepository;
import com.bhavin.market.model.CommonRepository;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {

    public static final int NO_MORE_DATA = 1;
    public static final int MORE_DATA_ADDED = 2;
    public static final int NEW_DATA_SET_ADDED = 3;
    public static final int DATA_FETCH_FAILED = 4;
    public static final int NO_SELLER_IN_CITY = 5;

    private final CommonRepository repository;
    private final AuthRepository repositoryAuth;
    private final SellersDataList sellersDataList = new SellersDataList();
    private String city;

    public HomeViewModel(@NonNull @NotNull Application application ){
        super(application);
        repository = new CommonRepository(application);
        repositoryAuth = new AuthRepository(application);
        sellersDataList.setData(new ArrayList<>());
    }

    public SellersDataList getSellersDataList( ){
        return sellersDataList;
    }

    public String getCity( ){
        return city;
    }

    public MutableLiveData<Pair<Boolean,Address>> addNewAddress(Address address){
        address.setEmail(Helper.user.getEmail());
        return repository.addNewAddress(address);
    }

    public MutableLiveData<Pair<Boolean,String>> changeCurrentAddress(String addressId){
        return repository.changeCurrentAddress(Helper.user.getEmail(), addressId);
    }

    public MutableLiveData<Integer> fetchSellersInCity(FragmentActivity activity, String city){
        MutableLiveData<Integer> fetchState = new MutableLiveData<>(null);
        String nextPageToken;
        int reqType;
        if(this.city == null){
            // Fetch the first page
            nextPageToken = null;
            reqType = 1;
        }else if(this.city.equals(city)){
            // Fetch the next Page of that city shops
            if(sellersDataList.getPagetoken() == null){
                fetchState.postValue(NO_MORE_DATA);
                return fetchState;
            }
            nextPageToken = String.valueOf(sellersDataList.getPagetoken());
            reqType = 2;
        }else{
            // Fetch the first page of given city
            nextPageToken = null;
            reqType = 3;
        }

        repository.fetchSellersInCity(city, nextPageToken)
                .observe(activity , booleanSellersDataListPair -> {
                    if(booleanSellersDataListPair.first){
                        if(booleanSellersDataListPair.second == null){
                            // Error
                            fetchState.postValue(DATA_FETCH_FAILED);
                        }else{
                            SellersDataList res = booleanSellersDataListPair.second;

                            if(reqType == 3 || reqType == 1){
                                sellersDataList.getData().clear();
                                if(res.getData() == null){
                                    fetchState.postValue(NO_SELLER_IN_CITY);
                                    res.setPagetoken(null);
                                }else{
                                    fetchState.postValue(NEW_DATA_SET_ADDED);
                                }
                            }else {
                                if(res.getData().size() == 0){
                                    fetchState.postValue(NO_MORE_DATA);
                                    res.setPagetoken(null);
                                }else{
                                    fetchState.postValue(MORE_DATA_ADDED);
                                    sellersDataList.setLastSize(sellersDataList.getSize());
                                }
                            }

                            sellersDataList.getData().addAll(res.getData());
                            sellersDataList.setPagetoken(res.getPagetoken());
                            this.city = city;

                            System.out.println(sellersDataList.getData());
                        }
                    }
                });

        return fetchState;
    }

    public void signOut(){
        repositoryAuth.signOut();
    }
}
