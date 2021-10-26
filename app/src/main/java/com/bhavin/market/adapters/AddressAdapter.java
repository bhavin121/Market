package com.bhavin.market.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhavin.market.Helper;
import com.bhavin.market.R;
import com.bhavin.market.classes.Address;
import com.bhavin.market.classes.User;
import com.bhavin.market.databinding.AddressCardBinding;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressVH> {

    private final List<Address> addressList;
    private int chosenIndex=0;

    public AddressAdapter(@NonNull List<Address> addressList){
        this.addressList = addressList;
        User user = Helper.user;

        swap(user.getCurrAddress());
    }

    public void swap(String currAddress){
        for ( int i=0;i<addressList.size(); i++) {
            if(addressList.get(i).getAddressId().equals(currAddress)){
                // swap this with 0th item
                Address address = addressList.get(i);
                addressList.set(i, addressList.get(0));
                addressList.set(0, address);
                notifyItemChanged(i);
                notifyItemChanged(0);
                chosenIndex = 0;
            }
        }
    }

    public int getChosenIndex( ){
        return chosenIndex;
    }

    public String getChosenAddressId(){
        return addressList.get(chosenIndex).getAddressId();
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
        holder.binding.address.setText(addressList.get(position).toString());
        if(position == chosenIndex){
            holder.binding.card.setStrokeWidth(6);
        }else{
            holder.binding.card.setStrokeWidth(0);
        }
    }

    @Override
    public int getItemCount(){
//        return 10;
        return addressList.size();
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
