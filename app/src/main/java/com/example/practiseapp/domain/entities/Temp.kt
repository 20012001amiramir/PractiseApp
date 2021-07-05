package com.example.practiseapp.domain.entities

import java.util.*

data class Temp(
    val id: Int = 0,
    val date: Calendar = Calendar.getInstance(),
    val temp: Double = 0.0
)