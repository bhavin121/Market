package com.bhavin.market;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhavin.market.databinding.FragmentProfileBinding;
import com.bhavin.market.viewModels.HomeViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private HomeViewModel viewModel;
    private Listener listener;

    public ProfileFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view , @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view , savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding.name.setText(MessageFormat.format("{0} {1}" , Helper.user.getFirstName() , Helper.user.getLastName()));

        binding.faq.setOnClickListener(view1 -> {
            startActivity(new Intent(requireContext(), FAQActivity.class));
        });

        binding.pastOrders.setOnClickListener(view1 -> {
            startActivity(new Intent(requireContext(), MyOrdersActivity.class));
        });

        binding.logOut.setOnClickListener(view1 -> {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());
            if(account!=null){
                viewModel.signOut();
            }else{

            }
            if(listener != null){
                listener.onLogOut();
            }
        });
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public interface Listener{
        void onLogOut();
    }
}