package com.bhavin.market.adapters;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhavin.market.R;
import com.bhavin.market.classes.Seller;
import com.bhavin.market.databinding.CategoriesBinding;
import com.bhavin.market.databinding.ShopCardBinding;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter< RecyclerView.ViewHolder > {

    public static final int ADVERTISER = 0;
    public static final int CATEGORIES = 1;
    public static final int SELLERS = 2;

    private List<Seller> items;

    public HomeAdapter(List < Seller > items){
        this.items = items;
    }

    public void setItems(List<Seller> items){
        this.items = items;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent , int viewType){
        switch (viewType){
            case ADVERTISER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertiser, parent, false);
                return new VH0(view);
            case CATEGORIES:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories, parent, false);
                return new VH1(view2);
            default:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_card, parent, false);
                return new VH2(view1);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder , int position){
        int p = position - 2;
        switch (getItemViewType(position)){
            case ADVERTISER:
                break;
            case CATEGORIES:
                break;
            default:
                VH2 vh2 = (VH2) holder;
                vh2.binding.shopName.setText(items.get(p).getEmail());

                Glide.with(vh2.itemView.getContext())
                        .load(items.get(p).getBannerUrl())
                        .centerCrop()
                        .placeholder(new ColorDrawable(Color.GRAY))
                        .into(vh2.binding.shopBanner);
                vh2.binding.shopAddress.setText(items.get(p).getStreetLane());
                vh2.binding.shopRating.setText(items.get(p).getAvgRating());
        }
    }

    @Override
    public int getItemCount( ){
        return items.size()+2;
    }

    @Override
    public int getItemViewType(int position){
        switch (position){
            case 0: return ADVERTISER;
            case 1: return CATEGORIES;
            default: return SELLERS;
        }
    }
}

class VH0 extends RecyclerView.ViewHolder{

    SliderView sliderView;

    public VH0(@NonNull @NotNull View itemView){
        super(itemView);
        sliderView = itemView.findViewById(R.id.slider);
        SliderAdapter adapter = new SliderAdapter(itemView.getContext());
        List <String> items = new ArrayList <>();
        items.add(String.valueOf(R.drawable.clothes_bag));
        items.add(String.valueOf(R.drawable.food_bag));
        items.add(String.valueOf(R.drawable.shoe_bag));
        items.add(String.valueOf(R.drawable.fruits_bag));
        adapter.setItems(items);

        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SCALE);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
    }
}

class VH1 extends RecyclerView.ViewHolder{

    CategoriesBinding binding;

    public VH1(@NonNull @NotNull View itemView){
        super(itemView);
        binding = CategoriesBinding.bind(itemView);
    }
}

class VH2 extends RecyclerView.ViewHolder{

    ShopCardBinding binding;

    public VH2(@NonNull @NotNull View itemView){
        super(itemView);
        binding = ShopCardBinding.bind(itemView);
    }
}
