package com.bhavin.market.viewModels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.bhavin.market.classes.Color;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class AddProductViewModel extends AndroidViewModel {

    private final List<Color> colors = new ArrayList<>();
    private final List<String> sizes = new ArrayList<>();
    private final List<Uri> uriList = new ArrayList<>();

    public AddProductViewModel(@NonNull @NotNull Application application){
        super(application);
    }

    public void addColor(Color color){
        colors.add(color);
        System.out.println(colors.toString());
    }

    public void addSize(String size){
        sizes.add(size);
        System.out.println(sizes.toString());
    }

    public void addImage(Uri uri){
        uriList.add(uri);
        System.out.println(uriList.toString());
    }

    public List<Color> getColors( ){
        return colors;
    }

    public List<String> getSizes( ){
        return sizes;
    }

    public List<Uri> getUriList( ){
        return uriList;
    }
}
