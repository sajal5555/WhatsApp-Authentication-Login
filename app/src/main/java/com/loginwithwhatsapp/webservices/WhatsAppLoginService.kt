package com.loginwithwhatsapp.webservices

import com.loginwithwhatsapp.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface WhatsAppLoginService {

    //OTPLess:SVXOKFBJWBXMJEWTSOERZDDHCGTEMQXC
    @POST("client/user/session/initiate")
    @Headers(
        "Content-Type: application/json",
        "appId: OTPLess:SVXOKFBJWBXMJEWTSOERZDDHCGTEMQXC"
    )
    suspend fun initiateLogin(@Body loginMethod: LoginInitiatePO): Response<LoginInitiateVO>


    //RHtRCsV7pZuXM9LkjXfydccRRhO4Z3yjUZpn6nIBUMmFpqTqS6TrH6UVFgMP6DQsJ
    @POST("client/user/session/userdata")
    @Headers(
        "Content-Type: application/json",
        "appId: OTPLess:SVXOKFBJWBXMJEWTSOERZDDHCGTEMQXC",
        "appSecret: RHtRCsV7pZuXM9LkjXfydccRRhO4Z3yjUZpn6nIBUMmFpqTqS6TrH6UVFgMP6DQsJ"
    )
    suspend fun verifyUSer(@Body token: VierfyUserPO): Response<VerifyUserVO>

}