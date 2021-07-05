package com.example.practiseapp.domain.usecases.AuthUseCases

import com.example.practiseapp.domain.repositories.IAuthRepository

class SignOutUseCase(
    private val authRepository: IAuthRepository
): ISignOutUseCase {
    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }
}
