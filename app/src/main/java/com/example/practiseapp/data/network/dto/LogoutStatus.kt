package com.example.practiseapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class LogoutStatus(
    @SerializedName("status")
    val status: String
)
