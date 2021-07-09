package com.example.practiseapp.data.network

import com.example.practiseapp.data.network.dto.UserGetResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserApi {

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @GET("user/{id}")
    suspend fun getUser(@Path("id") id: Long): Response<UserGetResponse>
}
