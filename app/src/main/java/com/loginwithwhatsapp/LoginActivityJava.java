package com.loginwithwhatsapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.loginwithwhatsapp.databinding.ActivityLoginBinding;
import com.otpless.main.OnOtplessResult;
import com.otpless.main.Otpless;
import com.otpless.main.OtplessIntentRequest;
import com.otpless.main.OtplessProvider;
import com.otpless.main.OtplessTokenData;

public class LoginActivityJava extends AppCompatActivity {

    // declare otpless variable
    private Otpless otpless = null;

    private ActivityLoginBinding binding = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());


        LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        if (getIntent().getData() != null && getIntent().getData().getQuery() != null) {
            viewModel.verifyWhatsAppLogin(getIntent().getData().getQuery().split("=")[1]);
        }

        //Initialize OTPLess Instance
        otpless = OtplessProvider.getInstance(this).init(new OnOtplessResult() {
            @Override
            public void onOtplessResult(OtplessTokenData otplessTokenData) {
                viewModel.onOtplessResult(otplessTokenData);

            }
        });


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.initiateWhatsAppLogin();

            }
        });

        viewModel.getIntentLiveData().observe(this, intent -> {
            if (intent != null) {
                initiateOtplessFlow(intent);
            }
        });

        viewModel.getVerifyUserData().observe(this, verifyUserData -> {
            if (verifyUserData != null) {
                binding.login.setVisibility(View.GONE);
                binding.successLogin.setVisibility(View.VISIBLE);
                binding.successLogin.setText("Successful login by ${it.name} with phone number XXXXXXXXXX");

            }
        });
    }

    void initiateOtplessFlow(String intentUri) {
        //While you create a request with otpless sdk you can define your own loading text and color
        OtplessIntentRequest request = new OtplessIntentRequest(intentUri); //.setLoadingText("Please wait...")
        request.setProgressBarColor(R.color.purple_200);
        if (otpless != null)
            otpless.openOtpless(request);
    }
}