package com.bhavin.market;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Toast;

import com.bhavin.market.classes.DataBaseError;
import com.bhavin.market.classes.SuccessMessage;
import com.bhavin.market.classes.User;
import com.bhavin.market.database.DataBaseConnection;
import com.bhavin.market.databinding.FragmentSignUpBinding;
import com.bhavin.market.viewModels.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.TimeUnit;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private AuthViewModel authViewModel;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);

        binding.logIn.setOnClickListener(view1-> Navigation.findNavController(view1).popBackStack());

        binding.signUp.setOnClickListener(view1 -> {
            if(binding.passwordField.getText().toString().equals(binding.confirmPasswordField.getText().toString())){
                goToDetailsPage(binding.userName.getText().toString(), binding.passwordField.getText().toString());
            }else{
                Toast.makeText(getContext(), "Confirm password do not match", Toast.LENGTH_LONG)
                        .show();
            }
        });

        binding.signUpButton.setOnClickListener(view1 -> {
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
            goToDetailsPage(account.getEmail(), account.getId());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            GoogleSignInAccount account = authViewModel.getSignedInAccountFromIntent(data);
            if(account == null){
                Toast.makeText(requireContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
            }else{
                goToDetailsPage(account.getEmail(), account.getId());
            }
        }
    }

    private void goToDetailsPage(String userName, String password) {
        authViewModel.setUserName(binding.userName.getText().toString());
        authViewModel.setPassword(binding.passwordField.getText().toString());
        Navigation.findNavController(binding.signUp).navigate(R.id.action_signUpFragment_to_addUserDetailsFragment);
    }
}