package com.bhavin.market.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.bhavin.market.DetailsFragment;
import com.bhavin.market.ProductListFragment;
import org.jetbrains.annotations.NotNull;

public class ProductsAndDetailsAdapter extends FragmentStateAdapter {

    private final Fragment[] fragments = {new ProductListFragment(), new DetailsFragment()};

    public ProductsAndDetailsAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
