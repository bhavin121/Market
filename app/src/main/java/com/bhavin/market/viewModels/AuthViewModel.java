package com.bhavin.market.viewModels;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bhavin.market.classes.DataBaseError;
import com.bhavin.market.classes.SuccessMessage;
import com.bhavin.market.classes.User;
import com.bhavin.market.model.AuthRepository;
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
    private String userName, phone, password, verificationId, firstName, lastName, gender;

    public AuthViewModel(@NonNull @NotNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        otpAuthResultsLiveData = new MutableLiveData<>(null);
        isOTPSent = new MutableLiveData<>(false);
    }

    public MutableLiveData<Boolean> sendOTP(FragmentActivity activity, String phone){
        authRepository.sendOTP(activity, phone)
                .observe(activity, otpStatus -> {
                    if(otpStatus.isOTPSent){
                        isOTPSent.postValue(true);
                        this.verificationId = otpStatus.verificationId;
                    }
                });
        return isOTPSent;
    }

    public MutableLiveData<Pair<User,DataBaseError>> logIn(String userName, String password){
        return authRepository.logIn(userName, password);
    }

    public MutableLiveData<Pair<SuccessMessage,DataBaseError>> signUp(){
        User user = new User();
        user.setEmail(userName);
        user.setPassword(password);
        user.setGender(gender);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setContactNo(phone);

        return authRepository.signUp(user);
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

    public String getUserName( ){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getFirstName( ){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName( ){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getGender( ){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
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
