package com.bhavin.market.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.bhavin.market.classes.Color;
import com.bhavin.market.databinding.ColorPopupBinding;
import java.util.List;

public class ColorPopUpAdapter extends BaseAdapter {

    private final List<Color> colors;
    private final LayoutInflater inflater;

    public ColorPopUpAdapter(List<Color> colors , LayoutInflater inflater){
        this.colors = colors;
        this.inflater = inflater;
    }

    public ColorPopUpAdapter(Context context, List<Color> colors){
        this.colors = colors;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount( ){
        return colors.size();
    }

    @Override
    public Object getItem(int i){
        return colors.get(i);
    }

    @Override
    public long getItemId(int i){
        return 0;
    }

    @Override
    public View getView(int i , View view , ViewGroup viewGroup){
        ColorPopupBinding binding;
        if(view == null){
            binding = ColorPopupBinding.inflate(inflater, viewGroup, false);
            view = binding.getRoot();
            binding.color.setImageTintList(ColorStateList.valueOf(android.graphics.Color.parseColor(colors.get(i).getColor())));
            binding.name.setText(colors.get(i).getName());
        }
        return view;
    }
}
