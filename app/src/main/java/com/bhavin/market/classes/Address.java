package com.bhavin.market.classes;

import javax.annotation.Generated;

import com.google.android.gms.common.util.ScopeUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Address {

    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("street_lane")
    @Expose
    private String streetLane;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longtitude")
    @Expose
    private String longtitude;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("flag_seller")
    @Expose
    private String flagSeller;
    @SerializedName("email")
    @Expose
    private String email;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetLane() {
        return streetLane;
    }

    public void setStreetLane(String streetLane) {
        this.streetLane = streetLane;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFlagSeller() {
        return flagSeller;
    }

    public void setFlagSeller(String flagSeller) {
        this.flagSeller = flagSeller;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSellerFlag(boolean flag){
        flagSeller = (flag)?"1":"0";
    }

    @Override
    public String toString(){
        return streetLane+","+city+","+state+","+ country+",\n"+pincode+"\nMo."+phoneNo;
    }
}