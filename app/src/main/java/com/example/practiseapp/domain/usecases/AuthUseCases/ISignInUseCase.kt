package com.example.practiseapp.domain.usecases.AuthUseCases

import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser

interface ISignInUseCase {
    suspend operator fun invoke(accountUser: AccountUser): Result<Boolean>
}
