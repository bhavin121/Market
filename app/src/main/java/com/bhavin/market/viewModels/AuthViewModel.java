package com.bhavin.market.viewModels;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.bhavin.market.model.AuthRepository;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import org.jetbrains.annotations.NotNull;

public class AuthViewModel extends AndroidViewModel {

    private int otp=0, inputPosition=0;
    private MutableLiveData<OTPData> otpData;
    private MutableLiveData<Task<AuthResult>> otpAuthResultsLiveData;
    private final MutableLiveData<Boolean> isOTPSent;
    private final AuthRepository authRepository;
    private String email, phone, password, verificationId;

    public AuthViewModel(@NonNull @NotNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        otpAuthResultsLiveData = new MutableLiveData<>(null);
        isOTPSent = new MutableLiveData<>(false);
    }

    public MutableLiveData<Boolean> sendOTP(FragmentActivity activity, String phone, String password){
        this.phone = phone;
        this.password = password;
        authRepository.sendOTP(activity, phone)
                .observe(activity, otpStatus -> {
                    if(otpStatus.isOTPSent){
                        isOTPSent.postValue(true);
                        this.verificationId = otpStatus.verificationId;
                    }
                });
        return isOTPSent;
    }

    public GoogleSignInAccount getLastSignedInAccount(){
        return authRepository.getLastSignedInAccount();
    }

    public GoogleSignInAccount getSignedInAccountFromIntent(Intent data){
        return authRepository.getSignedInAccountFromIntent(data);
    }

    public Intent getGoogleSignInIntent(){
        return authRepository.getGoogleSignInIntent();
    }

    public void validateOTP(){
        otpAuthResultsLiveData.postValue(authRepository.validateOTP(verificationId,String.valueOf(otp)));
    }

    public void signOutOTP(){
        authRepository.signOutOTP();
    }

    public MutableLiveData<Task<AuthResult>> getOtpAuthResultsLiveData() {
        return otpAuthResultsLiveData;
    }

    public MutableLiveData<OTPData> getOtpData() {
        if(otpData == null){
            otpData = new MutableLiveData<>();
            otpData.setValue(new OTPData(otp, inputPosition, true));
        }
        return otpData;
    }

    public void appendOTPInput(int n){
        if(otp/100000==0) {
            otp = otp * 10 + n;
            inputPosition++;
            otpData.setValue(new OTPData(otp, inputPosition, true));
            if(inputPosition == 6){
                validateOTP();
            }
        }
    }

    public void clearOTPLast(){
        if(otp!=0) {
            otp = otp / 10;
            inputPosition--;
            otpData.setValue(new OTPData(otp, inputPosition, false));
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }

    //******************************************************************//
    public static class OTPData{
        public int otp, position;
        public boolean isIncremented;

        public OTPData(int otp, int position, boolean isIncremented) {
            this.otp = otp;
            this.position = position;
            this.isIncremented = isIncremented;
        }
    }
    //******************************************************************//
    public static class OTPStatus{
        public boolean isOTPSent = false;
        public String verificationId;

        public OTPStatus(boolean isOTPSent, String verificationId) {
            this.isOTPSent = isOTPSent;
            this.verificationId = verificationId;
        }
    }
}
