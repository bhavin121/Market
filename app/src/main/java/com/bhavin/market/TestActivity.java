package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.bhavin.market.classes.DataBaseError;
import com.bhavin.market.classes.ProductDataList;
import com.bhavin.market.database.DataBaseConnection;
import com.bhavin.market.databinding.ActivityTestBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;
import java.util.UUID;

public class TestActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityTestBinding binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Checkout.preload(getApplicationContext());

        binding.pay.setOnClickListener(view -> startPayment());

        DataBaseConnection.fetchProducts(this , "bhavin121" , "2", new DataBaseConnection.ConnectionListener<ProductDataList>() {
            @Override
            public void onSuccess(ProductDataList productDataList){

            }

            @Override
            public void onFailure(DataBaseError error){

            }
        });

//        ColorPopUpAdapter adapter = new ColorPopUpAdapter(this, Arrays.asList(new Color("#fff0ff", "White"), new Color("#ff00ff", "Dummy"), new Color("#221122", "Test")));
//        binding.spinner.setAdapter(adapter);
    }

    public void startPayment(){
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_16mI1BmCTGSD1E");
        checkout.setImage(R.drawable.logo);
        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();
            options.put("name" , "Market");
            options.put("description" , "Reference No. #123456");
            options.put("image" , "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id" , UUID.randomUUID().toString());//from response of step 3.

//            System.out.println(UUID.randomUUID().toString());
            JSONObject theme = new JSONObject();
            theme.put("color", "#3612FF");
            options.put("theme", theme);

            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits

            JSONObject prefill = new JSONObject();
            prefill.put("email", "bhavin.kumar@gmail.com");
            prefill.put("contact","7014316242");
            options.put("prefill", prefill);

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s , PaymentData paymentData){
        System.out.println(paymentData.getData().toString());
    }

    @Override
    public void onPaymentError(int i , String s , PaymentData paymentData){
        System.out.println(s);
    }
}