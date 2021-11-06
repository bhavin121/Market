package com.bhavin.market.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhavin.market.R;
import com.bhavin.market.classes.Product;
import com.bhavin.market.databinding.ProductCardBinding;
import com.bumptech.glide.Glide;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ClickListener{
        void onProductClick(Product product);
        void onAddToCart(Product product);
    }

    public static final int PRODUCT = 0;
    public static final int LOADER = 1;

    private final List<Product> data;
    private boolean noMoreData = false;
    public ClickListener clickListener;

    public ProductListAdapter(List<Product> data){
        this.data = data;
    }

    public void setNoMoreData(boolean noMoreData){
        this.noMoreData = noMoreData;
        notifyItemChanged(data.size());
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType){
        RecyclerView.ViewHolder result;
        if(viewType==LOADER) {
            View view1 = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_bar_layout , parent , false);
            result = new VH3(view1);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_card , parent , false);
            result = new ProductVH(view);
        }
        return result;
    }

    @SuppressLint ("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder , int position){
        if(position == data.size()){
            VH3 vh3 = (VH3) holder;
            if(noMoreData) {
                vh3.binding.noMoreSeller.setVisibility(View.VISIBLE);
                vh3.binding.noMoreSeller.setText("No More Products");
                vh3.binding.progressBar.setVisibility(View.GONE);
            }
            else{
                vh3.binding.progressBar.setVisibility(View.VISIBLE);
                vh3.binding.noMoreSeller.setVisibility(View.GONE);
            }
        }else{
            ProductVH productVH = (ProductVH) holder;
            Product product = data.get(position);
            productVH.binding.rating.setText(product.getAvgRating());
            productVH.binding.name.setText(product.getName());
            productVH.binding.price.setText(product.getPrice());

            Glide.with(holder.itemView.getContext())
                    .load(product.getDisplayPhotoUrl())
                    .centerCrop()
                    .placeholder(new ColorDrawable(Color.GRAY))
                    .into(productVH.binding.productPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    @Override
    public int getItemViewType(int position){
        if(data.size() == position){
            return LOADER;
        }else{
            return PRODUCT;
        }
    }

    class ProductVH extends RecyclerView.ViewHolder{

        ProductCardBinding binding;

        public ProductVH(@NonNull @NotNull View itemView) {
            super(itemView);
            binding = ProductCardBinding.bind(itemView);
            itemView.setOnClickListener(view -> {
                if(clickListener == null) return;
                clickListener.onProductClick(data.get(getAdapterPosition()));
            });
            binding.addButton.setOnClickListener(view ->{
                if(clickListener == null) return;
                clickListener.onAddToCart(data.get(getAdapterPosition()));
            });
        }
    }
}
