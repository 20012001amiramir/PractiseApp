package com.example.practiseapp.data.network.dto


import com.google.gson.annotations.SerializedName

data class UserSignIn(
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)