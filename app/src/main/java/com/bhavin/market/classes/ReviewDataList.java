package com.bhavin.market.classes;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ReviewDataList {

    @SerializedName("data")
    @Expose
    private List<Review> data = null;
    @SerializedName("pagetoken")
    @Expose
    private Integer pagetoken;

    private int lastSize = 0;

    public ReviewDataList(){
        data = new ArrayList<>();
    }

    public List<Review> getData() {
        return data;
    }

    public void setData(List<Review> data) {
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

    public int getLastSize( ){
        return lastSize;
    }

    public void setLastSize(int lastSize){
        this.lastSize = lastSize;
    }

    public int getSize(){
        return data.size();
    }

    @Override
    public String toString(){
        return "ReviewDataList{" +
                "data=" + data +
                ", pagetoken=" + pagetoken +
                ", lastSize=" + lastSize +
                '}';
    }
}