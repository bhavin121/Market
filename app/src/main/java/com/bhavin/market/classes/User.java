package com.bhavin.market.classes;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("jsonschema2pojo")
public class User {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("curr_address")
    @Expose
    private String currAddress;
    @SerializedName("flag_seller")
    @Expose
    private String flagSeller;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("address")
    @Expose
    private List<Address> address = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCurrAddress() {
        return currAddress;
    }

    public void setCurrAddress(String currAddress) {
        this.currAddress = currAddress;
    }

    public String getFlagSeller() {
        return flagSeller;
    }

    public void setFlagSeller(String flagSeller) {
        this.flagSeller = flagSeller;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public boolean isSeller(){
        return (flagSeller.equals("1"))?true:false;
    }

    public void setFlagSeller(boolean var){
        flagSeller = (var)?"1":"0";
    }

    public List<Address> getAddress( ){
        return address;
    }

    public void setAddress(List<Address> address){
        this.address = address;
    }

    public Address getCurrentAddressObj(){
        for ( int i = 0 ; i < address.size() ; i++ ) {
            if(address.get(i).getAddressId().equals(currAddress)){
                return address.get(i);
            }
        }
        return null;
    }
}