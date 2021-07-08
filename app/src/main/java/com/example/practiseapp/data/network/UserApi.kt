package com.example.practiseapp.data.network

import com.example.practiseapp.data.network.dto.UserGetResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserApi {
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @GET("user/")
    suspend fun getUser(): Response<UserGetResponse>
}
