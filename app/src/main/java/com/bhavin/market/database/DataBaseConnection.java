package com.bhavin.market.database;

import android.content.Context;
import android.webkit.CookieManager;

import androidx.annotation.Nullable;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class DataBaseConnection {

    public static void validateUser(Context context, String contact, String password, ConnectionListener<User> listener){
        String url = "https://test.com/validateUser.php"; // Dummy Url

        Map<String, String> map = new HashMap<>();
        map.put("contact", contact);
        map.put("password",password);

        establishConnection(context, map, url, Request.Method.GET, listener, User.class);
    }

    public static void registerUser(Context context, User user, ConnectionListener<Result> listener){
        String url = "https://test.com/registerUser.php"; // Dummy Url

        Map<String, String> map = new HashMap<>();
        String json = new GsonBuilder().create().toJson(user); // Convert Object to JSON string
        map.put("user", json);

        establishConnection(context,map, url, Request.Method.POST, listener, Result.class);
    }

    private static<T> void establishConnection(Context context, Map<String, String> map, String url, int requestType, ConnectionListener<T> listener, Class<T> type){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(requestType, url,
                // Success Listener
                response -> {
                    if(response.contains("ERROR:")){
                        listener.onFailure(response.replaceFirst("ERROR:",""));
                    }
                    else{
                        Gson gson = new GsonBuilder().create();
                        T t = gson.fromJson(response, type);
                        listener.onSuccess(t);
                    }
                },
                // Error Listener
                error -> {
                    listener.onFailure("Something went wrong");
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                String cookie = CookieManager.getInstance().getCookie(url);
                map.put("cookie", cookie);
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

    public interface ConnectionListener<T>{
        void onSuccess(T t);
        void onFailure(String message);
    }
}
// Dummy Classes
class User{

}

class Result{

}