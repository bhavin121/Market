package com.bhavin.market.viewModels;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.bhavin.market.classes.DataBaseError;
import com.bhavin.market.classes.FAQ;
import com.bhavin.market.database.DataBaseConnection;

public class CommonRepository {

    private Application application;

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
}
