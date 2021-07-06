package com.example.practiseapp.data.network

import com.example.practiseapp.data.network.dto.UserSignInResponse
import com.example.practiseapp.data.network.dto.UserSignIn
import com.example.practiseapp.data.network.dto.UserSignUp
import com.example.practiseapp.data.network.dto.UserSignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

        @Headers(
            "Content-Type: application/json",
            "Accept: application/json"
        )
        @POST("registration/")
        suspend fun signUp(@Body dto: UserSignUp): Response<UserSignUpResponse>

        @Headers(
            "Content-Type: application/json",
            "Accept: application/json"
        )
        @POST("login/")
        suspend fun signIn(@Body dto: UserSignIn): Response<UserSignInResponse>
}
