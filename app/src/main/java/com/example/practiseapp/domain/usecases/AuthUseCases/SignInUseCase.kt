package com.example.practiseapp.domain.usecases.AuthUseCases

import com.example.practiseapp.data.di.qualifiers.AuthRepositoryMain
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.repositories.IAuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    @AuthRepositoryMain private val authRepository: IAuthRepository
): ISignInUseCase {
    override suspend fun invoke(accountUser: AccountUser): Result<String> =
        authRepository.signIn(accountUser)
}
