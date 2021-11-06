package com.bhavin.market.classes;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ProductDataList {

    @SerializedName("data")
    @Expose
    private List<Product> data = null;
    @SerializedName("pagetoken")
    @Expose
    private Integer pagetoken;

    private int lastSize = 0;

    public ProductDataList( ){
        data = new ArrayList<>();
    }

    public void setLastSize(int lastSize){
        this.lastSize = lastSize;
    }

    public int getLastSize( ){
        return lastSize;
    }

    public int getSize( ){
        return data.size();
    }

    public List<Product> getData( ) {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    public Integer getPagetoken() {
        return pagetoken;
    }

    public void setPagetoken(Integer pagetoken) {
        this.pagetoken = pagetoken;
    }

    public boolean isEmpty(){
        return data.isEmpty();
    }
}