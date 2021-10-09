package com.bhavin.market;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhavin.market.databinding.FragmentLogInBinding;
import com.bhavin.market.viewModels.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class LogInFragment extends Fragment {

    private FragmentLogInBinding binding;
    private AuthViewModel authViewModel;

    public LogInFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogInBinding.inflate(inflater, container, false);


        binding.signUp.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(
                    R.id.action_logInFragment_to_signUpFragment
            );
        });

        binding.logIn.setOnClickListener(view1 -> {
            startActivityForResult(authViewModel.getGoogleSignInIntent(),100);
        });

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = authViewModel.getLastSignedInAccount();
        if(account != null){
            goToHome(account.getEmail());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            GoogleSignInAccount account = authViewModel.getSignedInAccountFromIntent(data);
            if(account == null){
                Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show();
            }else{
                goToHome(account.getEmail());
            }
        }
    }

    private void goToHome(String email) {
        Toast.makeText(requireContext(), email, Toast.LENGTH_SHORT).show();
    }
}