package com.bhavin.market.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bhavin.market.classes.Address;
import com.bhavin.market.classes.Color;
import com.bhavin.market.classes.DataBaseError;
import com.bhavin.market.classes.FAQ;
import com.bhavin.market.classes.Product;
import com.bhavin.market.classes.ProductDataList;
import com.bhavin.market.classes.ReviewDataList;
import com.bhavin.market.classes.Seller;
import com.bhavin.market.classes.SellerData;
import com.bhavin.market.classes.SellersDataList;
import com.bhavin.market.classes.SuccessMessage;
import com.bhavin.market.classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseConnection {

    public static final String KEY = "pRW8lfLMVpiBVJLjHK15B8kd2";

    public static void validateUser(Context context, String email, String password, ConnectionListener<User> listener){
        String url = "http://44akash44.great-site.net/log_in.php"; // API Url

        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password",password);
        map.put("key", KEY);

        establishConnection(context, map, url, Request.Method.POST, listener, User.class);
    }

    public static void registerUser(Context context, User user, ConnectionListener< SuccessMessage > listener){
        String url = "http://44akash44.great-site.net/sign_up.php"; // API Url

        Map<String, String> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("first_name", user.getFirstName());
        map.put("last_name", user.getLastName());
        map.put("gender", user.getGender());
        map.put("password", user.getPassword());
        map.put("contact_no", user.getContactNo());
        map.put("key", KEY);

        establishConnection(context,map, url, Request.Method.POST, listener, SuccessMessage.class);
    }

    public static void registerSeller(Context context, Seller seller, Address address, ConnectionListener<SellerData> listener){
        String url = "http://44akash44.great-site.net/seller_registration.php"; // API Url

        Map<String, String> map = new HashMap<>();
        map.put("email", address.getEmail());
        map.put("pinCode", address.getPincode());
        map.put("city", address.getCity());
        map.put("state", address.getState());
        map.put("country", address.getCountry());
        map.put("street", address.getStreetLane());
        map.put("seller_flag", address.getFlagSeller());
        map.put("phone", address.getPhoneNo());
        map.put("lat", address.getLatitude());
        map.put("lng", address.getLongtitude());

        map.put("gst_no", seller.getGstNo());
        map.put("shop_name", seller.getShopName());
        map.put("start_timing", seller.getTimingStart());
        map.put("end_timing", seller.getTimingEnd());
        map.put("category", seller.getCategory());
        map.put("banner_url", seller.getBannerUrl());
        map.put("reg_date", seller.getRegistrationDate());
        map.put("key", KEY);

        establishConnection(context, map, url, Request.Method.POST, listener, SellerData.class);
    }

    public static void updateCurrentAddress(Context context, String userId, String addressId, ConnectionListener<SuccessMessage> listener){
        String url = "http://44akash44.great-site.net/update_current_address.php"; // API Url

        Map<String, String> map = new HashMap<>();
        map.put("key", KEY);
        map.put("email", userId);
        map.put("addressId", addressId);

        establishConnection(context, map, url, Request.Method.POST, listener, SuccessMessage.class);
    }

    public static void fetchSellersInCity(Context context, String city, String pageToken, ConnectionListener<SellersDataList> listener){
        String url = "http://44akash44.great-site.net/fetch_seller.php"; // API Url

        Map<String, String> map = new HashMap<>();
        map.put("key", KEY);
        map.put("city", city);
        if(pageToken != null) map.put("pagetoken", pageToken);

        establishConnection(context, map, url, Request.Method.POST, listener, SellersDataList.class);
    }

    public static void addNewProduct(Context context, Product product, List<String> photos, List<String> sizes, List<Color> colors, ConnectionListener<SuccessMessage> listener){
        String url = "http://44akash44.great-site.net/add-product1.php";

        Map<String, String> map = new HashMap<>();
        map.put("key", KEY);
        map.put("name", product.getName());
        map.put("offer", product.getOffer());
        map.put("description", product.getDescription());
        map.put("price",product.getPrice());
        map.put("available_units", product.getAvailableUnits());
        map.put("minimum_selling_quantity", product.getMinimumSellingQuantity());
        map.put("unit_of_selling", product.getUnitOfSelling());
        map.put("increment_size", product.getIncrementSize());

        int i=0;
        for ( String photo : photos ) {
            map.put("url["+i+"]", photo);
            i++;
        }

        i=0;
        for ( String size: sizes ){
            map.put("value["+i+"]", size);
            i++;
        }

        i=0;
        for ( Color color: colors ){
            map.put("colorName["+i+"]", color.getName());
            map.put("color["+i+"]", color.getColor());
            i++;
        }

        establishConnection(context, map, url, Request.Method.POST, listener, SuccessMessage.class);
    }

    public static void addNewAddress(Context context, Address address, ConnectionListener<SuccessMessage> listener){
        String url = "http://44akash44.great-site.net/add_address.php";
        Map<String, String> map = new HashMap<>();
        map.put("email", address.getEmail());
        map.put("pinCode", address.getPincode());
        map.put("city", address.getCity());
        map.put("state", address.getState());
        map.put("country", address.getCountry());
        map.put("street", address.getStreetLane());
        map.put("phone", address.getPhoneNo());
        map.put("lat", address.getLatitude());
        map.put("lng", address.getLongtitude());
        map.put("key", KEY);
        establishConnection(context, map, url, Request.Method.POST, listener, SuccessMessage.class);
    }

    public static void fetchFAQs(Context context, ConnectionListener<FAQ> listener){
        String url = "http://44akash44.great-site.net/ques-ans.php";
        establishConnection(context, null, url, Request.Method.POST, listener, FAQ.class);
    }

    public static void fetchSellerAddress(Context context, String userId, ConnectionListener<Address> listener){
        String url = "http://44akash44.great-site.net/fetch_address_of_seller.php"; // API Url

        Map<String, String> map = new HashMap<>();
        map.put("key", KEY);
        map.put("email", userId);

        establishConnection(context, map, url, Request.Method.POST, listener, Address.class);
    }

    public static void fetchReviews(Context context , String id ,String pageToken, ConnectionListener<ReviewDataList> listener){
        String url = "http://44akash44.great-site.net/review.php"; // API Url

        Map<String, String> map = new HashMap<>();
        map.put("key", KEY);
        map.put("email",id);
        if(pageToken != null) map.put("pagetoken", pageToken);

        establishConnection(context, map, url, Request.Method.POST, listener, ReviewDataList.class);
    }

    public static void fetchProducts(Context context, String sellerId, String pageToken, ConnectionListener<ProductDataList> listener){
        String url = "http://44akash44.great-site.net/seller_product.php"; // API Url

        Map<String, String> map = new HashMap<>();
        map.put("key", KEY);
        map.put("email",sellerId);
        if(pageToken != null) map.put("pagetoken", pageToken);

        establishConnection(context, map, url, Request.Method.POST, listener, ProductDataList.class);
    }

    @SuppressLint ("SetJavaScriptEnabled")
    private static <T> void establishConnection(Context context, Map<String, String> map, String url, int requestType, ConnectionListener<T> listener, Class<T> type){
        RequestQueue queue = Volley.newRequestQueue(context);
        WebView webView = new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view , String url){
                super.onPageFinished(view , url);
                StringRequest stringRequest = new StringRequest(requestType, url,
                        // Success Listener
                        response -> {
                            Gson gson = new GsonBuilder().create();
                            System.out.println(response);
                            if(response.contains("ERROR:")){
                                DataBaseError error = gson.fromJson(response.replaceFirst("ERROR:",""), DataBaseError.class);
                                listener.onFailure(error);
                            }
                            else{
                                T t = gson.fromJson(response, type);
                                listener.onSuccess(t);
                            }
                        },
                        // Error Listener
                        error -> {
                            System.out.println(error.getMessage());
                            DataBaseError error1 = new DataBaseError();
                            error1.setMessage("Something went wrong");
                            listener.onFailure(error1);
                        }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        String cookie = CookieManager.getInstance().getCookie(url);
                        map.put("Cookie", cookie);
                        System.out.println(cookie+" ");
                        return map;
                    }

                    @Nullable
                    @org.jetbrains.annotations.Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return map;
                    }
                };

                queue.add(stringRequest);
            }
        });
        webView.loadUrl(url);
    }

    public interface ConnectionListener<T>{
        void onSuccess(T t);
        void onFailure(DataBaseError error);
    }
}