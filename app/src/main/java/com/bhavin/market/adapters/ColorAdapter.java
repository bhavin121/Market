package com.bhavin.market.adapters;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhavin.market.R;
import com.bhavin.market.classes.Color;
import com.bhavin.market.databinding.ColorBinding;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorVH> {

    private final List<Color> colors;

    public ColorAdapter(List<Color> colors){
        this.colors = colors;
    }

    public void notifyChange(){
        notifyItemInserted(colors.size());
    }

    @NonNull
    @NotNull
    @Override
    public ColorVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent , int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color, parent, false);
        return new ColorVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ColorVH holder , int position){
        holder.binding.color.setImageTintList(ColorStateList.valueOf(android.graphics.Color.parseColor(colors.get(position).getColor())));
        holder.binding.name.setText(colors.get(position).getName());
    }

    @Override
    public int getItemCount(){
        return colors.size();
    }
}

class ColorVH extends RecyclerView.ViewHolder{

    ColorBinding binding;

    public ColorVH(@NonNull @NotNull View itemView){
        super(itemView);
        binding = ColorBinding.bind(itemView);
    }
}
