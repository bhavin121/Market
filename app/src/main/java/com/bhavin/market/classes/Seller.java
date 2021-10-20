package com.bhavin.market.classes;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Seller {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("registration_date")
    @Expose
    private String registrationDate;
    @SerializedName("timing_start")
    @Expose
    private String timingStart;
    @SerializedName("timing_end")
    @Expose
    private String timingEnd;
    @SerializedName("banner_url")
    @Expose
    private String bannerUrl;

    @SerializedName("gst_no")
    @Expose
    private String gstNo;
    @SerializedName("service_city")
    @Expose
    private String serviceCity;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("rating_id")
    @Expose
    private String ratingId;
    @SerializedName("review_id")
    @Expose
    private String reviewId;
    @SerializedName("fulfilled_orders")
    @Expose
    private String fulfilledOrders;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getTimingStart() {
        return timingStart;
    }

    public void setTimingStart(String timingStart) {
        this.timingStart = timingStart;
    }

    public String getTimingEnd() {
        return timingEnd;
    }

    public void setTimingEnd(String timingEnd) {
        this.timingEnd = timingEnd;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getServiceCity() {
        return serviceCity;
    }

    public void setServiceCity(String serviceCity) {
        this.serviceCity = serviceCity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getFulfilledOrders() {
        return fulfilledOrders;
    }

    public void setFulfilledOrders(String fulfilledOrders) {
        this.fulfilledOrders = fulfilledOrders;
    }

}