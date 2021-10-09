package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bhavin.market.adapters.OrderListAdapter;

public class MyOrdersActivity extends AppCompatActivity {

    RecyclerView ordersListRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        ordersListRV = findViewById(R.id.ordersList);
        ordersListRV.setLayoutManager(new LinearLayoutManager(this));
        OrderListAdapter adapter = new OrderListAdapter();
        ordersListRV.setAdapter(adapter);
    }
}