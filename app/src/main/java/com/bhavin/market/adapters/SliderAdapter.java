package com.bhavin.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bhavin.market.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderVH>{

    private Context context;
    private List <String> mSliderItems = new ArrayList <>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<String> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, parent, false);
        return new SliderVH(inflate);
    }


    @Override
    public void onBindViewHolder(SliderVH viewHolder , int position){
        String url = mSliderItems.get(position);
        viewHolder.image.setImageResource(Integer.parseInt(url));
    }

    @Override
    public int getCount( ){
        return mSliderItems.size();
    }
}

class SliderVH extends SliderViewAdapter.ViewHolder{

    ImageView image;

    public SliderVH(View itemView){
        super(itemView);
        image = itemView.findViewById(R.id.image);
    }
}