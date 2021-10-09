package com.bhavin.market.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhavin.market.R;

import org.jetbrains.annotations.NotNull;

public class ProductListAdapter extends RecyclerView.Adapter<ProductVH> {
    @NonNull
    @NotNull
    @Override
    public ProductVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card, parent, false);
        return new ProductVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class ProductVH extends RecyclerView.ViewHolder{

    public ProductVH(@NonNull @NotNull View itemView) {
        super(itemView);
    }
}
