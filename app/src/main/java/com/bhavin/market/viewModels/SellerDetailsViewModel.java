package com.bhavin.market.viewModels;

import android.app.Application;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.bhavin.market.classes.Address;
import com.bhavin.market.classes.ProductDataList;
import com.bhavin.market.classes.ReviewDataList;
import com.bhavin.market.classes.Seller;
import com.bhavin.market.model.CommonRepository;
import org.jetbrains.annotations.NotNull;

public class SellerDetailsViewModel extends AndroidViewModel {

    public static final int NO_MORE_DATA = 1;
    public static final int MORE_DATA_ADDED = 2;
    public static final int NEW_DATA_SET_ADDED = 3;
    public static final int DATA_FETCH_FAILED = 4;
    public static final int NO_REVIEW = 5;

    private final CommonRepository repository;
    private Seller seller = new Seller();
    private Address address;
    private final ReviewDataList reviewDataList = new ReviewDataList();
    private final ProductDataList productDataList = new ProductDataList();
    private boolean noReview = false;
    private boolean noProduct = false;

    public SellerDetailsViewModel(@NonNull @NotNull Application application){
        super(application);
        repository = new CommonRepository(application);
    }

    public void setSeller(Seller seller){
        this.seller = seller;
    }

    public Seller getSeller(){
        return seller;
    }

    public Address getAddress(){
        return address;
    }

    public ReviewDataList getReviewDataList( ){
        return reviewDataList;
    }

    public ProductDataList getProductDataList( ){
        return productDataList;
    }

    public boolean hasNoReview( ){
        return noReview;
    }

    public boolean hasNoProducts( ){
        return noProduct;
    }

    public MutableLiveData<Pair<Boolean,Boolean>> fetchSellerAddress(FragmentActivity activity){
        MutableLiveData<Pair<Boolean,Boolean>> res = new MutableLiveData<>(new Pair<>(false, null));
        repository.fetchSellerAddress(seller.getEmail())
                .observe(activity , booleanAddressPair -> {
                    if(booleanAddressPair.first){
                        if(booleanAddressPair.second == null){
                            // Error
                            res.postValue(new Pair<>(true, false));
                            address = new Address();
                        }else{
                            res.postValue(new Pair<>(true, true));
                            address = booleanAddressPair.second;
                        }
                    }
                });
        return res;
    }

    public MutableLiveData<Integer> fetchReviews(FragmentActivity activity){

        MutableLiveData<Integer> fetchState = new MutableLiveData<>(null);
        String nextPageToken;
        int reqType;

        if(reviewDataList.isEmpty()){
            // Fetch the first page
            nextPageToken = null;
            reqType = 1;
        }else{
            // Fetch the next Page of reviews
            if(reviewDataList.getPagetoken() == null){
                fetchState.postValue(NO_MORE_DATA);
                return fetchState;
            }
            nextPageToken = String.valueOf(reviewDataList.getPagetoken());
            reqType = 2;
        }

        repository.fetchReviews(seller.getEmail(), nextPageToken)
                .observe(activity , booleanReviewDataListPair -> {
                    if(booleanReviewDataListPair.first){
                        if(booleanReviewDataListPair.second == null){
                            // Error
                            fetchState.postValue(DATA_FETCH_FAILED);
                        }else{
                            ReviewDataList res = booleanReviewDataListPair.second;

                            if(reqType==1){
                                if(res.getData() == null){
                                    fetchState.postValue(NO_REVIEW);
                                    noReview = true;
                                }else{
                                    fetchState.postValue(NEW_DATA_SET_ADDED);
                                }
                            }else {
                                if(res.getData().size() == 0){
                                    fetchState.postValue(NO_MORE_DATA);
                                    res.setPagetoken(null);
                                }else{
                                    fetchState.postValue(MORE_DATA_ADDED);
                                    reviewDataList.setLastSize(reviewDataList.getSize());
                                }
                            }

                            reviewDataList.getData().addAll(res.getData());
                            reviewDataList.setPagetoken(res.getPagetoken());

                            System.out.println(reviewDataList.getData());
                        }
                    }
                });

        return fetchState;
    }

    public MutableLiveData<Integer> fetchProducts(FragmentActivity activity){

        MutableLiveData<Integer> fetchState = new MutableLiveData<>(null);
        String nextPageToken;
        int reqType;

        if(productDataList.isEmpty()){
            // Fetch the first page
            nextPageToken = null;
            reqType = 1;
        }else{
            // Fetch the next Page of reviews
            if(productDataList.getPagetoken() == null){
                fetchState.postValue(NO_MORE_DATA);
                return fetchState;
            }
            nextPageToken = String.valueOf(productDataList.getPagetoken());
            reqType = 2;
        }

        repository.fetchProductsOfSeller(seller.getEmail(), nextPageToken)
                .observe(activity, booleanProductDataListPair -> {
                    if(booleanProductDataListPair.first){
                        if(booleanProductDataListPair.second == null){
                            // Error
                            fetchState.postValue(DATA_FETCH_FAILED);
                        }else{
                            ProductDataList res = booleanProductDataListPair.second;

                            if(reqType==1){
                                if(res.getData() == null){
                                    fetchState.postValue(NO_REVIEW);
                                    noProduct = true;
                                }else{
                                    fetchState.postValue(NEW_DATA_SET_ADDED);
                                }
                            }else {
                                if(res.getData().size() == 0){
                                    fetchState.postValue(NO_MORE_DATA);
                                    res.setPagetoken(null);
                                }else{
                                    fetchState.postValue(MORE_DATA_ADDED);
                                    productDataList.setLastSize(productDataList.getSize());
                                }
                            }

                            productDataList.getData().addAll(res.getData());
                            productDataList.setPagetoken(res.getPagetoken());

                            System.out.println(productDataList.getData());
                    }
                }});


        return fetchState;
    }

}
