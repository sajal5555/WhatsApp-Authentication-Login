package com.loginwithwhatsapp.models

data class VerifyUserVO(
    val responseCode: String,
    val message: String,
    val data: VerifyUserData,
)

data class VerifyUserData(
    val name: String,
    val mobile: String,
    val stateMatched: Boolean,
    val orderId: String,
    val state: String?,
    val ip: String,
)

