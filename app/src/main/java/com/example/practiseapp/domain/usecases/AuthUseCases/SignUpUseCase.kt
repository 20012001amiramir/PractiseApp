package com.example.practiseapp.domain.usecases.AuthUseCases

import android.util.Log
import com.example.practiseapp.Constants
import com.example.practiseapp.data.di.qualifiers.AuthRepositoryMain
import com.example.practiseapp.data.di.qualifiers.SignInUseCaseMain
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.entities.LoggedUser
import com.example.practiseapp.domain.entities.SessionManager
import com.example.practiseapp.domain.repositories.IAuthRepository
import javax.inject.Inject
import kotlin.Exception

class SignUpUseCase @Inject constructor(
    @AuthRepositoryMain private val authRepository: IAuthRepository
): ISignUpUseCase {
    override suspend fun invoke(accountUser: AccountUser): Result<AccountUser> =
        authRepository.signUp(accountUser)
}
