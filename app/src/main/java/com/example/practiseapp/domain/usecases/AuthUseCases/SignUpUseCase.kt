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
    @AuthRepositoryMain private val authRepository: IAuthRepository,
    @SignInUseCaseMain private val signInUseCase: ISignInUseCase,
    private val loggedUser: LoggedUser
): ISignUpUseCase {
    override suspend fun invoke(accountUser: AccountUser): Result<Boolean> {
        return when (val accountUserResponse = authRepository.signUp(accountUser)) {
            is Result.Success -> {
                when (val result = signInUseCase(accountUser)) {
                    is Result.Success -> {
                        loggedUser.accountUser = AccountUser(
                            id = accountUser.id,
                            username = accountUser.username,
                            email = accountUser.email,
                            firstName = accountUser.firstName,
                            lastName = accountUser.lastName
                        )
                        Log.d(Constants.LOGGED_USER, "USER DATA: ${loggedUser.accountUser.toString()}")
                        Result.Success(true)
                    }
                    is Result.Failure -> Result.Failure(Exception(result.exception.message))
                }

            }
            is Result.Failure -> Result.Failure(Exception(accountUserResponse.exception.message))
        }

    }
}
