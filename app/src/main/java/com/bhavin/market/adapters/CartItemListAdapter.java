package com.bhavin.market.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhavin.market.R;

import org.jetbrains.annotations.NotNull;

public class CartItemListAdapter extends RecyclerView.Adapter<CIVHolder> {

    int size = 0;

    public CartItemListAdapter(int size){
        this.size = size;
    }

    @NonNull
    @NotNull
    @Override
    public CIVHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_in_cart_card, parent, false);
        return new CIVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CIVHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return size;
    }
}

class CIVHolder extends RecyclerView.ViewHolder{

    public CIVHolder(@NonNull @NotNull View itemView) {
        super(itemView);
    }
}