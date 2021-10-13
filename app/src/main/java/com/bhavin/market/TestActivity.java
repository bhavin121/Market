package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bhavin.market.classes.User;
import com.bhavin.market.databinding.ActivityTestBinding;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityTestBinding binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User user = Helper.user;
        StringBuilder builder = new StringBuilder();
        builder.append("First Name : ")
                .append(user.getFirstName())
                .append("\nLast Name : ")
                .append(user.getLastName())
                .append("\nGender : ")
                .append(user.getGender())
                .append("\nPhone No : ")
                .append(user.getContactNo())
                .append("\nUser Name : ")
                .append(user.getEmail())
                .append("\nPassword : ")
                .append(user.getPassword());
        binding.result.setText(builder.toString());
    }
}