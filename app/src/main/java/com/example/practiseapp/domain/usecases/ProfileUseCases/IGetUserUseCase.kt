package com.example.practiseapp.domain.usecases.ProfileUseCases

import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser

interface IGetUserUseCase {
    suspend operator fun invoke(): Result<AccountUser>
}
