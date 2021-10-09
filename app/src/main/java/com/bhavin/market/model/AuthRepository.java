package com.bhavin.market.model;

import android.app.Application;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import com.bhavin.market.viewModels.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.TimeUnit;

public class AuthRepository {

    private final Application application;
    private final FirebaseAuth firebaseAuth;
    private final GoogleSignInClient googleSignInClient;

    public AuthRepository(Application application){
        this.application = application;
        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(application, options);
    }

    public Task<AuthResult> validateOTP(String verificationId, String otpCode){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,otpCode);
        return firebaseAuth.signInWithCredential(credential);
    }

    public MutableLiveData<AuthViewModel.OTPStatus> sendOTP(FragmentActivity activity, String phone){

        MutableLiveData<AuthViewModel.OTPStatus> otpStatus = new MutableLiveData<>(new AuthViewModel.OTPStatus(false, null));

        firebaseAuth.getFirebaseAuthSettings().forceRecaptchaFlowForTesting(true);

        PhoneAuthOptions phoneAuthOptions =
                PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber("+91"+phone)
                        .setTimeout(120L, TimeUnit.SECONDS)
                        .setActivity(activity)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull @NotNull String verificationId, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpStatus.postValue(new AuthViewModel.OTPStatus(true, verificationId));
                            }
                        })
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);

        return otpStatus;
    }

    public void signOutOTP(){
        firebaseAuth.signOut();
    }

    public GoogleSignInAccount getLastSignedInAccount(){
        return GoogleSignIn.getLastSignedInAccount(application);
    }

    public GoogleSignInAccount getSignedInAccountFromIntent(Intent data){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try{
            return task.getResult(ApiException.class);
        } catch (ApiException e) {
            return null;
        }
    }

    public Intent getGoogleSignInIntent(){
        return googleSignInClient.getSignInIntent();
    }
}
