package com.bhavin.market.model;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.bhavin.market.classes.Address;
import com.bhavin.market.classes.DataBaseError;
import com.bhavin.market.classes.Seller;
import com.bhavin.market.classes.SuccessMessage;
import com.bhavin.market.database.DataBaseConnection;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SellerRegistrationRepository {

    private Application application;
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
                .addOnSuccessListener(taskSnapshot -> {
                    photoReference.getDownloadUrl().addOnSuccessListener(uri -> resultMutableLiveData.postValue(new UploadResult(true, true, uri)))
                            .addOnFailureListener(e -> resultMutableLiveData.postValue(new UploadResult(false, true, null)));
                })
                .addOnFailureListener(e -> resultMutableLiveData.postValue(new UploadResult(false, true, null)));

        return resultMutableLiveData;
    }

    public void registerSeller(Seller seller, Address address){
        DataBaseConnection.registerSeller(application , seller , address , new DataBaseConnection.ConnectionListener<SuccessMessage>() {
            @Override
            public void onSuccess(SuccessMessage successMessage){

            }

            @Override
            public void onFailure(DataBaseError error){

            }
        });
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
