package com.example.practiseapp.domain.usecases.AuthUseCases


import android.util.Log
import com.example.practiseapp.Constants
import com.example.practiseapp.data.di.qualifiers.AuthRepositoryMain
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.entities.SessionManager
import com.example.practiseapp.domain.repositories.IAuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    @AuthRepositoryMain private val authRepository: IAuthRepository,
    private val sessionManager: SessionManager
): ISignInUseCase {
    override suspend fun invoke(accountUser: AccountUser): Result<Boolean> {
        return when (val signInResponse = authRepository.signIn(accountUser)) {
            is Result.Success -> {
                sessionManager.saveAuthToken(signInResponse.data)
                Log.d(Constants.TOKEN_LOG, "TOKEN VALUE: ${sessionManager.fetchAuthToken()}")
                Result.Success(true)
            }
            is Result.Failure -> Result.Failure(Exception("Signed up but not logged in, because of ${signInResponse.exception.message}"))
        }
    }
}
