package com.bhavin.market.viewModels;

import android.app.Application;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bhavin.market.classes.FAQ;
import com.bhavin.market.model.CommonRepository;

import org.jetbrains.annotations.NotNull;

public class FAQViewModel extends AndroidViewModel {

    private CommonRepository repository;

    public FAQViewModel(@NonNull @NotNull Application application){
        super(application);
        repository = new CommonRepository(application);
    }

    public MutableLiveData<Pair<Boolean,FAQ>> getFAQs(){
        return repository.getFAQs();
    }
}
