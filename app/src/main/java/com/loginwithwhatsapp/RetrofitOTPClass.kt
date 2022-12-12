package com.loginwithwhatsapp

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitOTPClass {
    companion object {
        private val READ_TIMEOUT = 30L
        var retrofitOTP: Retrofit? = null
    }

    val client: Retrofit?
        get() {
            val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .connectTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            client.addInterceptor(logging)
            client.interceptors()

            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()
            if (retrofitOTP == null) {

                retrofitOTP = Retrofit.Builder()
                    .baseUrl("https://api.otpless.app/v1/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client.build())
                    .build()
            }
            return retrofitOTP
        }
}