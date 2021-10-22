package com.bhavin.market.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhavin.market.R;
import com.bhavin.market.classes.Address;
import com.bhavin.market.databinding.AddressCardBinding;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressVH> {

    private final List<Address> addressList;
    private int chosenIndex;

    public AddressAdapter(List<Address> addressList){
        this.addressList = addressList;
    }

    public int getChosenIndex( ){
        return chosenIndex;
    }

    public void setChosenIndex(int chosenIndex){
        this.chosenIndex = chosenIndex;
    }

    @NonNull
    @NotNull
    @Override
    public AddressVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent , int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_card, parent, false);
        return new AddressVH(view).lick(this);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AddressVH holder , int position){
//        holder.binding.address.setText(addressList.get(position).toString());
        if(position == chosenIndex){
            holder.binding.card.setStrokeWidth(6);
        }else{
            holder.binding.card.setStrokeWidth(0);
        }
    }

    @Override
    public int getItemCount(){
        return 10;
//        return addressList.size();
    }
}

class AddressVH extends RecyclerView.ViewHolder{

    AddressCardBinding binding;
    AddressAdapter addressAdapter;

    public AddressVH(@NonNull @NotNull View itemView){
        super(itemView);
        binding = AddressCardBinding.bind(itemView);

        itemView.setOnClickListener(view -> {
            addressAdapter.notifyItemChanged(addressAdapter.getChosenIndex());
            addressAdapter.setChosenIndex(getAdapterPosition());
            addressAdapter.notifyItemChanged(addressAdapter.getChosenIndex());
        });
    }

    public AddressVH lick(AddressAdapter addressAdapter){
        this.addressAdapter = addressAdapter;
        return this;
    }
}
