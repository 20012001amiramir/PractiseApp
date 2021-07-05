package com.example.practiseapp.domain.usecases.AuthUseCases

import com.example.practiseapp.data.network.dto.UserSignUpResponse

interface ISignOutUseCase {
    suspend operator fun invoke(): Result<Boolean>
}