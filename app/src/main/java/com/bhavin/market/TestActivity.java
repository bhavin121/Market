package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bhavin.market.adapters.ColorPopUpAdapter;
import com.bhavin.market.classes.Color;
import com.bhavin.market.databinding.ActivityTestBinding;
import java.util.Arrays;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityTestBinding binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ColorPopUpAdapter adapter = new ColorPopUpAdapter(this, Arrays.asList(new Color("#fff0ff", "White"), new Color("#ff00ff", "Dummy"), new Color("#221122", "Test")));
        binding.spinner.setAdapter(adapter);
    }
}