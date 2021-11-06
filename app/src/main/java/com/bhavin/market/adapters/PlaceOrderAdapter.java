package com.bhavin.market.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhavin.market.R;
import com.bhavin.market.databinding.ItemInCartCardBinding;
import com.bhavin.market.databinding.OrderLayoutFooterBinding;
import com.bhavin.market.databinding.OrderLayoutHeaderBinding;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.List;

public class PlaceOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int HEADER = 1;
    public static final int FOOTER = 2;
    public static final int ITEM = 3;

    private List<String> data = Arrays.asList("A","{","v","d");
    public ClickListener listener;

    public PlaceOrderAdapter(ClickListener clickListener){
        listener = clickListener;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent , int viewType){
        switch (viewType){
            case HEADER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout_header, parent, false);
                return new HeaderVH(view);
            case FOOTER:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout_footer, parent, false);
                return new FooterVH(view1);
            default:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_cart_card, parent, false);
                return new ItemVH(view2);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder , int position){

    }

    @Override
    public int getItemCount(){
        return data.size()+2;
    }

    @Override
    public int getItemViewType(int position){
        if(position == 0){
            return HEADER;
        }else if(position == data.size()+1){
            return FOOTER;
        }else{
            return ITEM;
        }
    }

    public interface ClickListener{
        void changeQuantity(String productId, String newQuantity);
        void deleteItem(String productId);
        void showProductDetails(String productId);
        void payNow();
    }

    static class HeaderVH extends RecyclerView.ViewHolder{

        OrderLayoutHeaderBinding binding;

        public HeaderVH(@NonNull @NotNull View itemView){
            super(itemView);
            binding = OrderLayoutHeaderBinding.bind(itemView);
        }
    }

    class FooterVH extends RecyclerView.ViewHolder{

        OrderLayoutFooterBinding binding;

        public FooterVH(@NonNull @NotNull View itemView){
            super(itemView);
            binding = OrderLayoutFooterBinding.bind(itemView);

            binding.payNow.setOnClickListener(view -> listener.payNow());
        }
    }

    class ItemVH extends RecyclerView.ViewHolder{

        ItemInCartCardBinding binding;

        public ItemVH(@NonNull @NotNull View itemView){
            super(itemView);
            binding = ItemInCartCardBinding.bind(itemView);

            binding.delete.setOnClickListener(view -> listener.deleteItem(data.get(getAdapterPosition())));
            itemView.setOnClickListener(view -> listener.showProductDetails(data.get(getAdapterPosition())));
            binding.counter.setChangeListener(newVal -> listener.changeQuantity(data.get(getAdapterPosition()),newVal));
        }
    }
}