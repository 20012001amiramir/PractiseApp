package com.example.practiseapp.domain.usecases.AuthUseCases

import com.example.practiseapp.data.di.qualifiers.AuthRepositoryMain
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.repositories.IAuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    @AuthRepositoryMain private val authRepository: IAuthRepository
): ISignUpUseCase {
    override suspend fun invoke(accountUser: AccountUser): Result<AccountUser> =
        authRepository.signUp(accountUser)
}
