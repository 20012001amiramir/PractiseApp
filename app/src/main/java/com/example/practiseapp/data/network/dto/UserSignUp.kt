package com.example.practiseapp.data.network.dto


import com.google.gson.annotations.SerializedName

data class UserSignUp(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)