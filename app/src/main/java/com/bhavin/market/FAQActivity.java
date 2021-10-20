package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Pair;
import android.widget.Toast;

import com.bhavin.market.adapters.FAQAdapter;
import com.bhavin.market.classes.FAQ;
import com.bhavin.market.databinding.ActivityFaqactivityBinding;
import com.bhavin.market.viewModels.FAQViewModel;

public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityFaqactivityBinding binding = ActivityFaqactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FAQViewModel viewModel = new ViewModelProvider(this).get(FAQViewModel.class);

        viewModel.getFAQs().observe(this , booleanFAQPair -> {
            if(booleanFAQPair.first){
                if(booleanFAQPair.second == null){
                    // Error
                    Toast.makeText(this , "This is some problem" , Toast.LENGTH_SHORT).show();
                }else{
                    // Success
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    binding.recyclerView.setAdapter(new FAQAdapter(booleanFAQPair.second));
                }
            }
        });
    }
}