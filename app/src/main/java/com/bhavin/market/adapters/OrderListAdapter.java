package com.bhavin.market.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhavin.market.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Adapter class for order list recycler view
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    @NonNull
    @NotNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Inflating view from layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_card, parent, false);
        return new OrderViewHolder(view); // returning view holder instance
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderViewHolder holder, int position) {
        // perform task on bind

    }

    @Override
    public int getItemCount() {
        return 10; // return the size of data set
    }
}

/**
 * Recycler view holder for orders list adapter
 */

class OrderViewHolder extends RecyclerView.ViewHolder{

    public TextView orderNo, quantity, totalAmount, date;
    public MaterialTextView deliveryStatus;
    public MaterialButton detailsButton;

    public OrderViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        /*
         * find all views and assigning them in
         * their respective reference variables
         */
        orderNo = itemView.findViewById(R.id.orderNo);
        quantity = itemView.findViewById(R.id.quantity);
        totalAmount = itemView.findViewById(R.id.totalAmount);
        date =  itemView.findViewById(R.id.date);
        deliveryStatus = itemView.findViewById(R.id.deliveryStatus);
        detailsButton =  itemView.findViewById(R.id.detailsButton);
    }
}
