package com.bhavin.market.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhavin.market.R;
import com.bhavin.market.classes.FAQ;
import com.bhavin.market.databinding.QuestionBinding;
import org.jetbrains.annotations.NotNull;

public class FAQAdapter extends RecyclerView.Adapter<FAQvh> {

    private final FAQ faq;

    public FAQAdapter(@NonNull FAQ faq){
        this.faq = faq;
    }

    @NonNull
    @NotNull
    @Override
    public FAQvh onCreateViewHolder(@NonNull @NotNull ViewGroup parent , int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question, parent, false);
        return new FAQvh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FAQvh holder , int position){
        holder.binding.question.setText(faq.getQuestion().get(position).getQue());
        holder.binding.ans.setText(faq.getQuestion().get(position).getAns());
    }

    @Override
    public int getItemCount(){
        return faq.getQuestion().size();
    }
}

class FAQvh extends RecyclerView.ViewHolder{

    public QuestionBinding binding;

    public FAQvh(@NonNull @NotNull View itemView){
        super(itemView);
        binding = QuestionBinding.bind(itemView);
    }
}