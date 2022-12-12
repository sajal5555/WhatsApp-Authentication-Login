package com.loginwithwhatsapp

import android.opengl.Visibility
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.loginwithwhatsapp.databinding.ActivityLoginBinding
import com.otpless.main.Otpless
import com.otpless.main.OtplessIntentRequest
import com.otpless.main.OtplessProvider
import com.otpless.main.OtplessTokenData


class LoginActivity : AppCompatActivity() {

    // declare otpless variable
    private var otpless: Otpless? = null

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: LoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        intent.data?.query?.let {
            viewModel.verifyWhatsAppLogin(it.split("=")[1])
        }

        //Initialize OTPLess Instance
        otpless = OtplessProvider.getInstance(this).init { response: OtplessTokenData? ->
            viewModel.onOtplessResult(
                response
            )
        }

        binding.login.setOnClickListener {
            viewModel.initiateWhatsAppLogin()
        }

        viewModel.intentLiveData.observe(this) { intent ->
            intent?.let {
                initiateOtplessFlow(it)
            }
        }

        viewModel.verifyUserData.observe(this) { verifyUserData ->
            verifyUserData?.let {
                binding.login.visibility = GONE
                binding.successLogin.visibility = VISIBLE
                binding.successLogin.text = "Successful login by ${it.name} with phone number XXXXXXXXXX"
            }
        }
    }

    fun initiateOtplessFlow(intentUri: String) {
        //While you create a request with otpless sdk you can define your own loading text and color
        val request: OtplessIntentRequest =
            OtplessIntentRequest(intentUri) //.setLoadingText("Please wait...")
                .setProgressBarColor(R.color.purple_200)
        otpless?.openOtpless(request)
    }
}