package com.loginwithwhatsapp

data class OtpLessInitiate(
    val responseCode: String,
    val message: String,
    val data: OtpLessInitiateData,
)


data class OtpLessInitiateData(
    val intent: String
)
data class OtpLessInitiateRequest(
    val loginMethod: String
)