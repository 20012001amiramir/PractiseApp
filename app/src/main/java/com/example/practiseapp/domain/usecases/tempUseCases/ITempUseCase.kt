package com.example.practiseapp.domain.usecases.tempUseCases

import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.entities.Temp

interface ITempUseCase {
    suspend operator fun invoke(Temp: Temp): Result<Temp>
}
