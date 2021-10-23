package com.bhavin.market.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhavin.market.R;
import com.bhavin.market.databinding.ChosenItemBinding;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class ImageItemAdapter extends RecyclerView.Adapter<ImageVH> {

    private final List<Uri> images;
    public Listener listener;

    public ImageItemAdapter(List<Uri> images , Listener listener){
        this.images = images;
        this.listener = listener;
    }

    public List<Uri> getImages( ){
        return images;
    }

    @NonNull
    @NotNull
    @Override
    public ImageVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent , int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chosen_item, parent, false);
        return new ImageVH(view).link(this);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImageVH holder , int position){
        holder.binding.photo.setImageURI(images.get(position));
    }

    @Override
    public int getItemCount( ){
        return images.size();
    }

    public interface Listener{
        void onListEmpty();
    }
}

class ImageVH extends RecyclerView.ViewHolder{

    ChosenItemBinding binding;
    private ImageItemAdapter adapter;

    public ImageVH(@NonNull @NotNull View itemView){
        super(itemView);
        binding = ChosenItemBinding.bind(itemView);

        binding.delete.setOnClickListener(view -> {
            int p = getAdapterPosition();
            adapter.getImages().remove(p);
            adapter.notifyItemRemoved(p);

            if(adapter.getImages().size() == 0){
                adapter.listener.onListEmpty();
            }
        });
    }

    public ImageVH link(ImageItemAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}