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
                authViewModel.sendOTP(requireActivity(), binding.phoneField.getText().toString(),binding.passwordField.getText().toString())
                                .observe(requireActivity(), aBoolean -> {
                                    if(aBoolean){
                                        /**
                                         * On OTP is sent, navigate to OTP Auth Fragment
                                         */
                                        Navigation.findNavController(view1).navigate(R.id.action_signUpFragment_to_OTPAuthFragment);
                                    }
                                });
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

//    private void sendOtp(View view1) {
//
//        String phoneNo = binding.phoneField.getText().toString();
//        String passwordStr = binding.passwordField.getText().toString();
//
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.getFirebaseAuthSettings().forceRecaptchaFlowForTesting(true);
//
//        PhoneAuthOptions phoneAuthOptions =
//                PhoneAuthOptions.newBuilder(auth).setPhoneNumber("+91"+phoneNo)
//                .setTimeout(120L, TimeUnit.SECONDS)
//                .setActivity(requireActivity())
//                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
//
//                    }
//
//                    @Override
//                    public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
//
//                    }
//
//                    @Override
//                    public void onCodeSent(@NonNull @NotNull String verificationId, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString(PHONE, phoneNo);
//                        bundle.putString(PASSWORD, passwordStr);
//                        bundle.putString(VERIFICATION_ID, verificationId);
//                        Navigation.findNavController(view1).navigate(R.id.action_signUpFragment_to_OTPAuthFragment, bundle);
//                    }
//                })
//                .build();
//
//        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
//    }

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
                Toast.makeText(requireContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
            }else{
                goToHome(account.getEmail());
            }
        }
    }

    private void goToHome(String email) {

    }
}