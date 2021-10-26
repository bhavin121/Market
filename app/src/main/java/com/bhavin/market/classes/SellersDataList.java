package com.bhavin.market.classes;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class SellersDataList {

    @SerializedName("data")
    @Expose
    private List<Seller> data = null;
    @SerializedName("pagetoken")
    @Expose
    private Integer pagetoken;

    private int lastSize = 0;

    public List<Seller> getData() {
        return data;
    }

    public void setData(List<Seller> data) {
        this.data = data;
    }

    public Integer getPagetoken() {
        return pagetoken;
    }

    public void setPagetoken(Integer pagetoken) {
        this.pagetoken = pagetoken;
    }

    public void setLastSize(int lastSize){
        this.lastSize = lastSize;
    }

    public int getLastSize(){
        return lastSize;
    }

    public int getSize(){
        return data.size();
    }
}