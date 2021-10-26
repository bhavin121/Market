package com.bhavin.market.viewModels;

import android.app.Application;
import android.net.Uri;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bhavin.market.classes.Color;
import com.bhavin.market.classes.Product;
import com.bhavin.market.model.SellerRegistrationRepository;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class AddProductViewModel extends AndroidViewModel {

    private final List<Color> colors = new ArrayList<>();
    private final List<String> sizes = new ArrayList<>();
    private final List<Uri> uriList = new ArrayList<>();
    private SellerRegistrationRepository repository;

    public AddProductViewModel(@NonNull @NotNull Application application){
        super(application);
        repository = new SellerRegistrationRepository(application);
    }

    public void addColor(Color color){
        colors.add(color);
        System.out.println(colors.toString());
    }

    public void addSize(String size){
        sizes.add(size);
        System.out.println(sizes.toString());
    }

    public void addImage(Uri uri){
        uriList.add(uri);
        System.out.println(uriList.toString());
    }

    public List<Color> getColors( ){
        return colors;
    }

    public List<String> getSizes( ){
        return sizes;
    }

    public List<Uri> getUriList( ){
        return uriList;
    }

    public MutableLiveData<Pair<Boolean,Product>> addNewProduct(Product product, FragmentActivity activity){
        MutableLiveData<Pair<Boolean, Product>> res = new MutableLiveData<>(new Pair<>(false, null));
        repository.uploadImageList(uriList).observe(activity , booleanListPair -> {
            if(booleanListPair.first){
                if(booleanListPair.second == null){
                    //Error occurred
                    res.postValue(new Pair<>(true, null));
                }else{
                    repository.addNewProduct(product, booleanListPair.second, sizes, colors)
                    .observe(activity , res::postValue);
                }
            }
        });

        return res;
    }
}
