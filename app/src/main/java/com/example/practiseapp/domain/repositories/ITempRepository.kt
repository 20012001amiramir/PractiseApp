package com.example.practiseapp.domain.repositories

import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.entities.Temp

interface ITempRepository {

    suspend fun getTemp(Temp: Temp): Double

}