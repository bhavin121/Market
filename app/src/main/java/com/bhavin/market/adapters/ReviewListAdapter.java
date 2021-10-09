package com.bhavin.market.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhavin.market.R;
import org.jetbrains.annotations.NotNull;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewVH> {
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

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}

class ReviewVH extends RecyclerView.ViewHolder{

    public ReviewVH(@NonNull @NotNull View itemView) {
        super(itemView);
    }
}
