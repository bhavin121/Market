package com.bhavin.market;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.bhavin.market.adapters.ReviewListAdapter;
import com.bhavin.market.databinding.FragmentDetailsBinding;
import com.bhavin.market.viewModels.HomeViewModel;
import com.bhavin.market.viewModels.SellerDetailsViewModel;

import org.jetbrains.annotations.NotNull;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private SellerDetailsViewModel viewModel;
    private ReviewListAdapter adapter;
    private boolean isFetchingReviews;
    private boolean isScrolling = false;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SellerDetailsViewModel.class);

        adapter = new ReviewListAdapter(viewModel.getReviewDataList().getData());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.reviewListRV.setLayoutManager(manager);
        binding.reviewListRV.setAdapter(adapter);

        viewModel.fetchSellerAddress(requireActivity()).observe(requireActivity() , booleanBooleanPair -> {
            if(booleanBooleanPair.first){
                if(booleanBooleanPair.second){
                    // Success
                    binding.address.setText(viewModel.getAddress().toString());
                    binding.timing.setText(viewModel.getSeller().getTiming());
                }else{
                    // Failed
                    Toast.makeText(requireContext() , "Something went wrong" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        fetchReviews();

        binding.reviewListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    fetchReviews();
                }
            }
        });
    }

    public void fetchReviews(){
        if(isFetchingReviews || viewModel.hasNoReview()) return;

//        binding.progressBar.setVisibility(View.VISIBLE);
//        binding.noMoreSeller.setVisibility(View.GONE);
        isFetchingReviews = true;
        viewModel.fetchReviews(requireActivity())
                .observe(requireActivity() , integer -> {
                    if(integer == null) return;
                    isFetchingReviews = false;
//                    binding.progressBar.setVisibility(View.GONE);
                    switch (integer){
                        case HomeViewModel.NEW_DATA_SET_ADDED:
                            adapter.notifyDataSetChanged();
                            break;
                        case HomeViewModel.NO_MORE_DATA:
//                            binding.noMoreSeller.setVisibility(View.VISIBLE);
                            Toast.makeText(requireContext() , "No More Data" , Toast.LENGTH_SHORT).show();
                            break;
                        case HomeViewModel.NO_SELLER_IN_CITY:
                            break;
                        case HomeViewModel.MORE_DATA_ADDED:
                            int count = viewModel.getReviewDataList().getSize() - viewModel.getReviewDataList().getLastSize();
                            adapter.notifyItemRangeInserted(viewModel.getReviewDataList().getLastSize()+2, count);
                            break;
                        case HomeViewModel.DATA_FETCH_FAILED:
                            Toast.makeText(requireContext() , "Something went wrong" , Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
    }
}