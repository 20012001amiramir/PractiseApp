package com.example.practiseapp.domain.usecases.AuthUseCases

import com.example.practiseapp.data.di.qualifiers.AuthRepositoryMain
import com.example.practiseapp.data.network.dto.LogoutStatus
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.repositories.IAuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    @AuthRepositoryMain private val authRepository: IAuthRepository
): ISignOutUseCase {
    override suspend fun invoke(): Result<LogoutStatus> = authRepository.signOut()
}
