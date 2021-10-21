package com.bhavin.market.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhavin.market.R;
import com.bhavin.market.databinding.SizeBinding;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeVH> {

    private final List<String> sizes;

    public SizeAdapter(List<String> sizes){
        this.sizes = sizes;
    }

    public void notifyChange(){
        notifyItemInserted(sizes.size());
    }

    @NonNull
    @NotNull
    @Override
    public SizeVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent , int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.size, parent, false);
        return new SizeVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SizeVH holder , int position){
        holder.binding.size.setText(sizes.get(position));
    }

    @Override
    public int getItemCount(){
        return sizes.size();
    }
}

class SizeVH extends RecyclerView.ViewHolder{

    SizeBinding binding;

    public SizeVH(@NonNull @NotNull View itemView){
        super(itemView);
        binding = SizeBinding.bind(itemView);
    }
}
