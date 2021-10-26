package com.bhavin.market.model;

import android.app.Application;
import android.net.Uri;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.bhavin.market.Helper;
import com.bhavin.market.classes.Address;
import com.bhavin.market.classes.Color;
import com.bhavin.market.classes.DataBaseError;
import com.bhavin.market.classes.Product;
import com.bhavin.market.classes.Seller;
import com.bhavin.market.classes.SellerData;
import com.bhavin.market.classes.SuccessMessage;
import com.bhavin.market.database.DataBaseConnection;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SellerRegistrationRepository {

    private final Application application;
    private final FirebaseStorage firebaseStorage;

    public SellerRegistrationRepository(Application application){
        this.application = application;
        firebaseStorage = FirebaseStorage.getInstance();
    }

    public MutableLiveData<UploadResult> uploadImage(Uri photo){
        MutableLiveData<UploadResult> resultMutableLiveData = new MutableLiveData<>(new UploadResult());
        StorageReference reference = firebaseStorage.getReference();
        StorageReference photoReference = reference.child("images/"+ UUID.randomUUID().toString());

        photoReference.putFile(photo)
                .addOnSuccessListener(taskSnapshot -> photoReference.getDownloadUrl().addOnSuccessListener(uri -> resultMutableLiveData.postValue(new UploadResult(true, true, uri)))
                        .addOnFailureListener(e -> resultMutableLiveData.postValue(new UploadResult(false, true, null))))
                .addOnFailureListener(e -> resultMutableLiveData.postValue(new UploadResult(false, true, null)));

        return resultMutableLiveData;
    }

    public MutableLiveData<Pair<Boolean,List<String>>> uploadImageList(List<Uri> uriList){
        StorageReference reference = firebaseStorage.getReference();
        List<String> photoUrlList = new ArrayList<>();
        MutableLiveData<Pair<Boolean, List<String>>> res = new MutableLiveData<>(new Pair<>(false, null));

        for(Uri uri:uriList) {
            StorageReference photoReference = reference.child("images/"+ UUID.randomUUID().toString());

            photoReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                photoReference.getDownloadUrl().addOnSuccessListener(uri1 -> {
                    photoUrlList.add(uri1.toString());

                    if(photoUrlList.size() == uriList.size()){
                        // All uploaded
                        res.postValue(new Pair<>(true, photoUrlList));
                    }
                }).addOnFailureListener(e -> {
                    res.postValue(new Pair<>(true, null));
                });
            }).addOnFailureListener(e -> {
                res.postValue(new Pair<>(true, null));
            });
        }

        return res;
    }

    public MutableLiveData<Pair<Boolean,Product>> addNewProduct(Product product, List<String> photos, List<String> sizes, List<Color> colors){
        MutableLiveData<Pair<Boolean, Product>> res = new MutableLiveData<>(new Pair<>(false, null));
        DataBaseConnection.addNewProduct(application , product , photos , sizes , colors , new DataBaseConnection.ConnectionListener<SuccessMessage>() {
            @Override
            public void onSuccess(SuccessMessage successMessage){
                product.setProductId(successMessage.getData());
                res.postValue(new Pair<>(true, product));
            }

            @Override
            public void onFailure(DataBaseError error){
                res.postValue(new Pair<>(true, null));
            }
        });

        return res;
    }

    public MutableLiveData<Pair<Boolean,SellerData>> registerSeller(Seller seller, Address address){
        MutableLiveData<Pair<Boolean,SellerData>> res = new MutableLiveData<>(new Pair<>(false, null));
        DataBaseConnection.registerSeller(application , seller , address , new DataBaseConnection.ConnectionListener<SellerData>() {
            @Override
            public void onSuccess(SellerData sellerData){
                res.postValue(new Pair<>(true, sellerData));
                Helper.user.setFlagSeller(true);
            }

            @Override
            public void onFailure(DataBaseError error){
                res.postValue(new Pair<>(true, null));
            }
        });
        return res;
    }

    public static class UploadResult{
        public boolean uploadSuccess = false;
        public boolean wasUploading = false;
        public Uri photoUrl;

        public UploadResult(){
        }

        public UploadResult(boolean uploadSuccess , boolean wasUploading, Uri photoUrl){
            this.uploadSuccess = uploadSuccess;
            this.wasUploading = wasUploading;
            this.photoUrl = photoUrl;
        }
    }
}
