package com.loginwithwhatsapp.models

data class LoginInitiateVO(
    val responseCode: String,
    val message: String,
    val data: LoginInitiateData,
)


data class LoginInitiateData(
    val intent: String
)