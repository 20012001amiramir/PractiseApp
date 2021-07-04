package com.example.practiseapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class UserSignInResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("token")
    val token: String
) {
    data class Data(
        @SerializedName("email")
        val email: String,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("last_name")
        val lastName: String,
        @SerializedName("username")
        val username: String
    )
}