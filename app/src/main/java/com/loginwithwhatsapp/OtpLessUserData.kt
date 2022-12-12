package com.loginwithwhatsapp

data class OtpLessUserData(
    val responseCode: String,
    val message: String,
    val data: OtpLessUserDataValue,
)


data class OtpLessUserDataValue(
    val name: String,
    val mobile: String,
    val stateMatched: Boolean,
    val orderId: String,
    val state: String?,
    val ip: String,
)
data class OtpLessUserDataValueRequest(
    val token: String,
)