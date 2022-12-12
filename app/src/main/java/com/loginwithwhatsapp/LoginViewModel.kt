package com.loginwithwhatsapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loginwithwhatsapp.models.LoginInitiatePO
import com.loginwithwhatsapp.models.VerifyUserData
import com.loginwithwhatsapp.models.VierfyUserPO
import com.loginwithwhatsapp.webservices.RetrofitOTPClass
import com.loginwithwhatsapp.webservices.WhatsAppLoginService
import com.otpless.main.OtplessTokenData
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val intentLiveData = MutableLiveData<String?>(null)
    val verifyUserData = MutableLiveData<VerifyUserData?>(null)

    fun initiateWhatsAppLogin() {
        viewModelScope.launch {
            val response =
                RetrofitOTPClass().client?.create(WhatsAppLoginService::class.java)?.initiateLogin(
                    LoginInitiatePO("WHATSAPP")
                )
            response?.body()?.data?.intent?.let { intentLiveData.postValue(it) }
        }
    }

    fun verifyWhatsAppLogin(token: String) {
        viewModelScope.launch {
            val response =
                RetrofitOTPClass.retrofitOTP?.create(WhatsAppLoginService::class.java)?.verifyUSer(
                    VierfyUserPO(token)
                )
            verifyUserData.postValue(response?.body()?.data)
        }
    }

    //Call back function Where token is received
    fun onOtplessResult(response: OtplessTokenData?) {
        if (response == null) return

        //Send this token to your backend end api to fetch user details from otpless service
        val token: String = response.token
        verifyWhatsAppLogin(token)
    }

}