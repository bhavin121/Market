package com.bhavin.market;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;
import com.bhavin.market.adapters.ProductListAdapter;
import com.bhavin.market.classes.Product;
import com.bhavin.market.databinding.FragmentProductListBinding;
import com.bhavin.market.viewModels.HomeViewModel;
import com.bhavin.market.viewModels.SellerDetailsViewModel;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

public class ProductListFragment extends Fragment {

    private FragmentProductListBinding binding;
    private ProductListAdapter adapter;

    private boolean isFetchingProducts;
    private boolean isScrolling = false;
    private SellerDetailsViewModel viewModel;

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentProductListBinding.bind(view);

        viewModel = new ViewModelProvider(requireActivity()).get(SellerDetailsViewModel.class);

        adapter = new ProductListAdapter(viewModel.getProductDataList().getData());
        adapter.setClickListener(new ProductListAdapter.ClickListener() {
            @Override
            public void onProductClick(Product product){
                Intent intent = new Intent(requireContext(), ProductDetailsActivity.class);
                intent.putExtra(ProductDetailsActivity.PRODUCT_ID, product.getProductId());
                startActivity(intent);
            }

            @Override
            public void onAddToCart(Product product){

            }
        });
        GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        binding.productListRV.setLayoutManager(manager);
        binding.productListRV.setAdapter(adapter);

        fetchProducts();

        binding.productListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView , int newState){
                super.onScrollStateChanged(recyclerView , newState);
                if(newState ==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView , int dx , int dy){
                super.onScrolled(recyclerView , dx , dy);
                int visibleItems = manager.getChildCount();
                int totalItems = manager.getItemCount();
                int scrolledOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && (scrolledOutItems+visibleItems == totalItems)){
                    isScrolling = false;
                    fetchProducts();
                }
            }
        });
    }

    private void fetchProducts(){
        if(isFetchingProducts || viewModel.hasNoProducts()) return;

        adapter.setNoMoreData(false);
        isFetchingProducts = true;
        viewModel.fetchProducts(requireActivity())
                .observe(requireActivity() , integer -> {
                    if(integer == null) return;
                    isFetchingProducts = false;
                    switch (integer){
                        case HomeViewModel.NEW_DATA_SET_ADDED:
                            adapter.notifyDataSetChanged();
                            adapter.setNoMoreData(false);
                            break;
                        case HomeViewModel.NO_MORE_DATA:
                            adapter.setNoMoreData(true);
                            break;
                        case HomeViewModel.NO_SELLER_IN_CITY:
                            break;
                        case HomeViewModel.MORE_DATA_ADDED:
                            int count = viewModel.getProductDataList().getSize() - viewModel.getProductDataList().getLastSize();
                            adapter.notifyItemRangeInserted(viewModel.getProductDataList().getLastSize(), count);
                            break;
                        case HomeViewModel.DATA_FETCH_FAILED:
                            Toast.makeText(requireContext() , "Something went wrong" , Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
    }
}