package com.example.practiseapp.domain.usecases.AuthUseCases

import com.example.practiseapp.domain.common.Result

interface ISignOutUseCase {
    suspend operator fun invoke(): Result<Int>
}
