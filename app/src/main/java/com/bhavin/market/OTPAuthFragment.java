package com.bhavin.market;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bhavin.market.customViews.LoadingDialogBuilder;
import com.bhavin.market.databinding.FragmentOTPAuthBinding;
import com.bhavin.market.viewModels.AuthViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import org.jetbrains.annotations.NotNull;
import java.text.MessageFormat;

public class OTPAuthFragment extends Fragment {

    private final AppCompatImageView []inputViews = new AppCompatImageView[6];

    private FragmentOTPAuthBinding binding;
    private AuthViewModel authViewModel;

    private final int[] keyIds = {
                                R.id.key0, R.id.key1, R.id.key2, R.id.key3,
                                R.id.key4, R.id.key5, R.id.key6, R.id.key7,
                                R.id.key8, R.id.key9
                            };

    public OTPAuthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOTPAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        authViewModel.getOtpData().observe(requireActivity(), otpData -> {
            if(!(otpData.isIncremented && otpData.position==0)){
                if(otpData.isIncremented){
                    input(otpData.position-1);
                }else{
                    clearLast(otpData.position);
                }
                if(otpData.position==6) show();
            }
        });

        authViewModel.getOtpAuthResultsLiveData().observe(requireActivity(), new Observer<Task<AuthResult>>() {
            @Override
            public void onChanged(Task<AuthResult> authResultTask) {
                if(authResultTask != null){
                    authResultTask.addOnSuccessListener(authResult -> {
                        Navigation.findNavController(inputViews[0]).navigate(R.id.action_OTPAuthFragment_to_addUserDetailsFragment);
                        authViewModel.signOutOTP();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Entered Wrong OTP", Toast.LENGTH_LONG)
                                .show();
                    });
                }
            }
        });

        inputViews[0] = binding.item1;
        inputViews[1] = binding.item2;
        inputViews[2] = binding.item3;
        inputViews[3] = binding.item4;
        inputViews[4] = binding.item5;
        inputViews[5] = binding.item6;

        if (getArguments() != null) {
            binding.message.setText(MessageFormat.format("OTP sent to +91 {0}", authViewModel.getPhone()));
        }

        for(int i=0;i<10;i++){
            view.findViewById(keyIds[i]).setOnClickListener(clickListener);
        }

        binding.keyBackSpace.setOnClickListener(view1 -> {
            authViewModel.clearOTPLast();
        });

    }

    private final View.OnClickListener clickListener = view -> {
        authViewModel.appendOTPInput(find(view.getId()));
    };

    public void show(){
        AlertDialog dialog = LoadingDialogBuilder.build(requireContext());
        dialog.show();
    }

    public void input(int inputPosition){
        Animation inputAnim = AnimationUtils.loadAnimation(getContext(), R.anim.input_anim);
        inputViews[inputPosition].setImageTintList(ColorStateList.valueOf(Color.BLUE));
        inputViews[inputPosition].startAnimation(inputAnim);
    }

//    private void validateOTP() {
//        Bundle args = getArguments();
//        if (args != null) {
//            String verificationId = args.getString(SignUpFragment.VERIFICATION_ID);
//            String password = args.getString(SignUpFragment.PASSWORD);
//            String phone = args.getString(SignUpFragment.PHONE);
//
//            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, String.valueOf(6000/*OTP Value here*/));
//            FirebaseAuth auth = FirebaseAuth.getInstance();
//            auth.signInWithCredential(credential)
//                    .addOnSuccessListener(authResult -> {
//                        Bundle bundle = new Bundle();
//                        bundle.putString(SignUpFragment.PASSWORD, password);
//                        bundle.putString(SignUpFragment.PHONE, phone);
//                        Navigation.findNavController(inputViews[0]).navigate(R.id.action_OTPAuthFragment_to_addUserDetailsFragment);
//                        auth.signOut();
//                    })
//                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Entered Wrong OTP", Toast.LENGTH_LONG)
//                    .show());
//        }
//    }

    public void clearLast(int inputPosition){
        Animation outputAnim = AnimationUtils.loadAnimation(getContext(), R.anim.output_anim);
        inputViews[inputPosition].setImageTintList(null);
        inputViews[inputPosition].startAnimation(outputAnim);
    }

    public int find(int id){
        for(int i=0;i<keyIds.length;i++){
            if(keyIds[i]==id){
                return i;
            }
        }
        return -1;
    }
}