package com.bhavin.market;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhavin.market.databinding.FragmentAddUserDetailsBinding;
import com.bhavin.market.viewModels.AuthViewModel;

import org.jetbrains.annotations.NotNull;

public class AddUserDetailsFragment extends Fragment {

    private FragmentAddUserDetailsBinding binding;
    private AuthViewModel authViewModel;

    public AddUserDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddUserDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        binding.submitButton.setOnClickListener(view1 -> {

            authViewModel.sendOTP(requireActivity(),binding.phoneField.getText().toString())
                    .observe(requireActivity() , aBoolean -> {
                        if(aBoolean){
                            // OTP sent successfully
                            authViewModel.setGender(getGender());
                            authViewModel.setFirstName(binding.firstName.getText().toString());
                            authViewModel.setLastName(binding.lastName.getText().toString());
                            authViewModel.setPhone(binding.phoneField.getText().toString());

                            Navigation.findNavController(view1).navigate(R.id.action_addUserDetailsFragment_to_OTPAuthFragment);
                        }else{
                            Toast.makeText(requireContext() , "Something went wrong" , Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    private String getGender( ){
        if(binding.gender.getCheckedRadioButtonId() == R.id.male){
            return "M";
        }else{
            return "F";
        }
    }
}