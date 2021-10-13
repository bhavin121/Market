package com.bhavin.market;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.Toast;

import com.bhavin.market.classes.DataBaseError;
import com.bhavin.market.classes.User;
import com.bhavin.market.database.DataBaseConnection;
import com.bhavin.market.databinding.FragmentLogInBinding;
import com.bhavin.market.viewModels.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.GsonBuilder;

public class LogInFragment extends Fragment{

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
            goToHome(binding.userNameField.getText().toString(), binding.passwordField.getText().toString());
        });

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = authViewModel.getLastSignedInAccount();
        if(account != null){
            goToHome(account.getEmail(), account.getId());
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
                goToHome(account.getEmail(), account.getId());
            }
        }
    }

    private void goToHome(String userName, String password) {
        authViewModel.logIn(userName, password)
                .observe(requireActivity() , userDataBaseErrorPair -> {
                    if(userDataBaseErrorPair != null){
                        if(userDataBaseErrorPair.first==null & userDataBaseErrorPair.second != null){
                            // Error occurred
                            Toast.makeText(requireContext() , userDataBaseErrorPair.second.getMessage() , Toast.LENGTH_SHORT).show();
                        }else if(userDataBaseErrorPair.second==null & userDataBaseErrorPair.first != null){
                            // Log in success
                            Helper.user = userDataBaseErrorPair.first;
                            startActivity(new Intent(requireContext(), TestActivity.class));
                        }
                    }
                });
    }
}