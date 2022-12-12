package com.loginwithwhatsapp

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.loginwithwhatsapp.databinding.ActivityLoginBinding
import com.otpless.main.Otpless
import com.otpless.main.OtplessIntentRequest
import com.otpless.main.OtplessProvider
import com.otpless.main.OtplessTokenData
import kotlinx.coroutines.*


class LoginActivity : AppCompatActivity() {

    // declare otpless variable
    private var otpless: Otpless? = null

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val query: String? = intent.data?.query
        var tokenValue: String? = null
        if (query != null) {
            tokenValue = query.split("=")[1]
            verifyWhatsAppLogin(tokenValue)
        }
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login?.setOnClickListener {
            initiateWhatsAppLogin()
        }
        //Initialize OTPLess Instance
        otpless = OtplessProvider.getInstance(this).init { response: OtplessTokenData? ->
            onOtplessResult(
                response
            )
        }
    }

    protected val scope = CoroutineScope(
        Job() + Dispatchers.Main
    )

    private fun initiateWhatsAppLogin() {
        scope.launch {
            val response =
                RetrofitOTPClass().client?.create(WhatsAppLoginService::class.java)?.initiateLogin(
                    OtpLessInitiateRequest("WHATSAPP")
                )
            response?.body()?.data?.intent?.let { initiateOtplessFlow(it) }
        }
    }

    private fun verifyWhatsAppLogin(token: String) {
        scope.launch {
            val response =
                RetrofitOTPClass.retrofitOTP?.create(WhatsAppLoginService::class.java)?.verifyUSer(
                    OtpLessUserDataValueRequest(token)
                )
            withContext(Dispatchers.Main) {
                Toast.makeText(this@LoginActivity, response?.message(), Toast.LENGTH_LONG).show()
            }
        }
    }

    //Call back function Where token is received
    private fun onOtplessResult(@Nullable response: OtplessTokenData?) {
        if (response == null) return

        //Send this token to your backend end api to fetch user details from otpless service
        val token: String = response.token
        verifyWhatsAppLogin(token)
    }

    private fun initiateOtplessFlow(intentUri: String) {

        //While you create a request with otpless sdk you can define your own loading text and color
        val request: OtplessIntentRequest =
            OtplessIntentRequest(intentUri) //.setLoadingText("Please wait...")
                .setProgressBarColor(R.color.purple_200)
        otpless?.openOtpless(request)
    }
}