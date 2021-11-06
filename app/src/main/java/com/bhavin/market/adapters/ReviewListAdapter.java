package com.bhavin.market.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhavin.market.R;
import com.bhavin.market.classes.Review;
import com.bhavin.market.databinding.ReviewItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewVH> {

    private List<Review> data;

    public ReviewListAdapter(List<Review> data){
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public ReviewVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReviewVH holder, int position) {
        holder.binding.message.setText(data.get(position).getReview());
        holder.binding.name.setText(data.get(position).getName());
        holder.binding.ratingBar.setRating(Float.parseFloat(data.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class ReviewVH extends RecyclerView.ViewHolder{

    ReviewItemBinding binding;

    public ReviewVH(@NonNull @NotNull View itemView) {
        super(itemView);
        binding = ReviewItemBinding.bind(itemView);
    }
}
