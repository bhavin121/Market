package com.bhavin.market.model;

import android.app.Application;
import android.util.Pair;
import androidx.lifecycle.MutableLiveData;

import com.bhavin.market.Helper;
import com.bhavin.market.classes.Address;
import com.bhavin.market.classes.DataBaseError;
import com.bhavin.market.classes.FAQ;
import com.bhavin.market.classes.ProductDataList;
import com.bhavin.market.classes.ReviewDataList;
import com.bhavin.market.classes.SellersDataList;
import com.bhavin.market.classes.SuccessMessage;
import com.bhavin.market.database.DataBaseConnection;

public class CommonRepository {

    private final Application application;

    public CommonRepository(Application application){
        this.application = application;
    }

    public MutableLiveData<Pair<Boolean,FAQ>> getFAQs(){
        MutableLiveData<Pair<Boolean, FAQ>> res = new MutableLiveData<>(new Pair<>(false, null));
        DataBaseConnection.fetchFAQs(application , new DataBaseConnection.ConnectionListener<FAQ>() {
            @Override
            public void onSuccess(FAQ faq){
                res.postValue(new Pair<>(true, faq));
            }

            @Override
            public void onFailure(DataBaseError error){
                res.postValue(new Pair<>(true, null));
            }
        });
        return res;
    }

    public MutableLiveData<Pair<Boolean,String>> changeCurrentAddress(String userId, String addressId){
        MutableLiveData<Pair<Boolean, String>> res = new MutableLiveData<>(new Pair<>(false, null));
        DataBaseConnection.updateCurrentAddress(application , userId , addressId , new DataBaseConnection.ConnectionListener<SuccessMessage>() {
            @Override
            public void onSuccess(SuccessMessage successMessage){
                Helper.user.setCurrAddress(addressId);
                res.postValue(new Pair<>(true, addressId));
            }

            @Override
            public void onFailure(DataBaseError error){
                res.postValue(new Pair<>(true, null));
            }
        });
        return res;
    }

    public MutableLiveData<Pair<Boolean,Address>> addNewAddress(Address address){
        MutableLiveData<Pair<Boolean, Address>> res = new MutableLiveData<>(new Pair<>(false, null));
        DataBaseConnection.addNewAddress(application , address , new DataBaseConnection.ConnectionListener<SuccessMessage>() {
            @Override
            public void onSuccess(SuccessMessage successMessage){
                address.setAddressId(successMessage.getData());
                address.setSellerFlag(false);
                res.postValue(new Pair<>(true, address));
            }

            @Override
            public void onFailure(DataBaseError error){
                res.postValue(new Pair<>(true, null));
            }
        });

        return res;
    }

    public MutableLiveData<Pair<Boolean,SellersDataList>> fetchSellersInCity(String city, String nextPageToken){
        MutableLiveData<Pair<Boolean, SellersDataList>> res = new MutableLiveData<>(new Pair<>(false, null));
        DataBaseConnection.fetchSellersInCity(application , city , nextPageToken , new DataBaseConnection.ConnectionListener<SellersDataList>() {
            @Override
            public void onSuccess(SellersDataList sellersDataList){
                res.postValue(new Pair<>(true, sellersDataList));
            }

            @Override
            public void onFailure(DataBaseError error){
                res.postValue(new Pair<>(true, null));
            }
        });
        return res;
    }

    public MutableLiveData<Pair<Boolean,Address>> fetchSellerAddress(String userId){
        MutableLiveData<Pair<Boolean, Address>> res = new MutableLiveData<>(new Pair<>(false, null));
        DataBaseConnection.fetchSellerAddress(application , userId , new DataBaseConnection.ConnectionListener<Address>() {
            @Override
            public void onSuccess(Address address){
                res.postValue(new Pair<>(true, address));
            }

            @Override
            public void onFailure(DataBaseError error){
                res.postValue(new Pair<>(true, null));
            }
        });
        return res;
    }

    public MutableLiveData<Pair<Boolean,ReviewDataList>> fetchReviews(String id, String nextPageToken){
        MutableLiveData<Pair<Boolean, ReviewDataList>> res = new MutableLiveData<>(new Pair<>(false, null));
        DataBaseConnection.fetchReviews(application , id , nextPageToken , new DataBaseConnection.ConnectionListener<ReviewDataList>() {
            @Override
            public void onSuccess(ReviewDataList reviewDataList){
                res.postValue(new Pair<>(true, reviewDataList));
            }

            @Override
            public void onFailure(DataBaseError error){
                res.postValue(new Pair<>(true, null));
            }
        });

        return res;
    }

    public MutableLiveData<Pair<Boolean,ProductDataList>> fetchProductsOfSeller(String sellerId, String nextPageToken){
        MutableLiveData<Pair<Boolean, ProductDataList>> res = new MutableLiveData<>(new Pair<>(false, null));
        DataBaseConnection.fetchProducts(application , sellerId , nextPageToken , new DataBaseConnection.ConnectionListener<ProductDataList>() {
            @Override
            public void onSuccess(ProductDataList productDataList){
                res.postValue(new Pair<>(true, productDataList));
            }

            @Override
            public void onFailure(DataBaseError error){
                res.postValue(new Pair<>(true, null));
            }
        });
        return res;
    }
}
