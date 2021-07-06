package com.example.practiseapp.data.repositories

import android.util.Log
import com.example.practiseapp.Constants
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.repositories.IAuthRepository
import com.example.practiseapp.data.network.AuthApi
import com.example.practiseapp.data.network.dto.LogoutStatus
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.data.network.mappers.AccountUserApiMapper
import com.example.practiseapp.domain.entities.LoggedUser
import com.example.practiseapp.domain.entities.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager,
    private val loggedUser: LoggedUser
) : IAuthRepository {
    // TODO add token and user data persistence
    override suspend fun signIn(accountUser: AccountUser): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val userResponse = authApi.signIn(
                    AccountUserApiMapper.toUserSignInDto(accountUser)
                )
                return@withContext if (userResponse.isSuccessful) {
                    val token = userResponse.body()!!.token
                    // sessionManager.saveAuthToken(token)
                    // loggedUser
                    Log.d(Constants.TOKEN_LOG, "TOKEN VALUE: $token")
                    Result.Success(token)
                } else {
                    Result.Failure(Exception(userResponse.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Failure(e)
            }
        }


    override suspend fun signUp(accountUser: AccountUser): Result<AccountUser> =
        withContext(Dispatchers.IO) {
            try {
                val userResponse = authApi.signUp(
                    AccountUserApiMapper.toUserSignUpDto(accountUser)
                )
                return@withContext if (userResponse.isSuccessful) {
                    Result.Success(AccountUserApiMapper.toUser(userResponse.body()!!))
                } else {
                    Result.Failure(Exception(userResponse.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Failure(e)
            }

        }

    override suspend fun signOut(): Result<LogoutStatus> {
        TODO("Not yet implemented")
    }
    //TODO:Rewrite with exceptions
}
