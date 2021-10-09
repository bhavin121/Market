package com.bhavin.market.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhavin.market.R;
import com.bhavin.market.databinding.ShopCardBinding;
import org.jetbrains.annotations.NotNull;

public class ShopListAdapter extends RecyclerView.Adapter<ShopVH> {
    @NonNull
    @NotNull
    @Override
    public ShopVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ShopVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShopVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class ShopVH extends RecyclerView.ViewHolder{

    public ShopCardBinding binding;

    public ShopVH(@NonNull @NotNull View itemView) {
        super(itemView);
        binding = ShopCardBinding.bind(itemView);
    }
}
