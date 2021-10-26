package com.bhavin.market.classes;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Product {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("available_units")
    @Expose
    private String availableUnits;
    @SerializedName("minimum_selling_quantity")
    @Expose
    private String minimumSellingQuantity;
    @SerializedName("unit_of_selling")
    @Expose
    private String unitOfSelling;
    @SerializedName("increment_size")
    @Expose
    private String incrementSize;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailableUnits() {
        return availableUnits;
    }

    public void setAvailableUnits(String availableUnits) {
        this.availableUnits = availableUnits;
    }

    public String getMinimumSellingQuantity() {
        return minimumSellingQuantity;
    }

    public void setMinimumSellingQuantity(String minimumSellingQuantity) {
        this.minimumSellingQuantity = minimumSellingQuantity;
    }

    public String getUnitOfSelling() {
        return unitOfSelling;
    }

    public void setUnitOfSelling(String unitOfSelling) {
        this.unitOfSelling = unitOfSelling;
    }

    public String getIncrementSize() {
        return incrementSize;
    }

    public void setIncrementSize(String incrementSize) {
        this.incrementSize = incrementSize;
    }

    @Override
    public String toString( ){
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", offer='" + offer + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", availableUnits='" + availableUnits + '\'' +
                ", minimumSellingQuantity='" + minimumSellingQuantity + '\'' +
                ", unitOfSelling='" + unitOfSelling + '\'' +
                ", incrementSize='" + incrementSize + '\'' +
                '}';
    }
}