package com.bhavin.market;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AddProductPhotos extends Fragment {

    public static final int PICK_IMAGE = 100;

    private MaterialButton choose, next;
    private TextView noImageText;
    private LinearLayout chosenImagesList;
    private int uploadCount = 0;
    private final ArrayList<String> photos = new ArrayList<>();

    public AddProductPhotos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product_photos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find all views
        choose = view.findViewById(R.id.pickImageButton);
        next = view.findViewById(R.id.next);
        noImageText = view.findViewById(R.id.noImageChosen);
        chosenImagesList = view.findViewById(R.id.chosenImageContainer);

        choose.setOnClickListener((view1 -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 100);
        }));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null){
            Uri photo = data.getData();
            addPhoto(photo);
            uploadPhoto(photo);
            Toast.makeText(requireContext(), photo.getPath(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPhoto(Uri photo) {

        if(photo == null){
            Toast.makeText(requireContext(), "Pick A Photo", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference reference = firebaseStorage.getReference();

        StorageReference photoReference = reference.child("images/"+ UUID.randomUUID().toString());

        ProgressDialog dialog = new ProgressDialog(requireContext());
        dialog.setMessage("Uploading Photo...");
        dialog.setCancelable(false);
        dialog.show();

        photoReference.putFile(photo)
                .addOnSuccessListener(taskSnapshot -> {
                    dialog.dismiss();
                    uploadCount++;
                    photos.add(photoReference.getPath());
                    Toast.makeText(requireContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    dialog.dismiss();
                    chosenImagesList.removeViewAt(chosenImagesList.getChildCount()-1);
                    Toast.makeText(requireContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                });
    }

    private void addPhoto(Uri photo) {
        noImageText.setVisibility(View.GONE);
        View view = getLayoutInflater().inflate(R.layout.image_item, chosenImagesList, false);
        ((AppCompatImageView)view.findViewById(R.id.photo)).setImageURI(photo);
        chosenImagesList.addView(view);
    }
}