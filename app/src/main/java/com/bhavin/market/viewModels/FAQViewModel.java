package com.bhavin.market.viewModels;

import android.app.Application;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.bhavin.market.classes.FAQ;
import org.jetbrains.annotations.NotNull;

public class FAQViewModel extends AndroidViewModel {

    private CommonRepository repository;

    public FAQViewModel(@NonNull @NotNull Application application){
        super(application);
        repository = new CommonRepository(application);
    }

    public MutableLiveData<Pair<Boolean,FAQ>> getFAQs(){
        MutableLiveData<Pair<Boolean, FAQ>> res = new MutableLiveData<>(new Pair<>(false, null));
        repository.getFAQs().observe(getApplication() , booleanFAQPair -> {
            if(booleanFAQPair.first){
                res.postValue(new Pair<>(true, booleanFAQPair.second));
            }
        });
        return res;
    }
}
