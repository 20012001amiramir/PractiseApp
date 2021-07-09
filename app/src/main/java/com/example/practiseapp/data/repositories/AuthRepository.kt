package com.example.practiseapp.data.repositories

import android.util.Log
import com.example.practiseapp.Constants
import com.example.practiseapp.data.db.user.UserDao
import com.example.practiseapp.data.mappers.AccountUserApiMapper
import com.example.practiseapp.data.mappers.UserEntityMapper
import com.example.practiseapp.data.network.AuthApi
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.entities.LoggedUser
import com.example.practiseapp.domain.repositories.IAuthRepository
import com.example.practiseapp.domain.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager,
    private val loggedUser: LoggedUser,
    private val userDao: UserDao
) : IAuthRepository {

    override suspend fun signIn(accountUser: AccountUser): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val userResponse = authApi.signIn(
                    AccountUserApiMapper.toUserSignInDto(accountUser)
                )
                val responseBody = userResponse.body()
                return@withContext if (userResponse.isSuccessful &&  responseBody != null) {
                    val token = responseBody.token
                    val userIdFromDB = userDao.insertUser(UserEntityMapper.toUserEntity(responseBody))
                    val userFromDB = userDao.getUser(userIdFromDB)
                    sessionManager.saveAuthToken(token)
                    sessionManager.saveLoggedUserId(userIdFromDB)
                    if (userFromDB != null) {
                        loggedUser.accountUser = UserEntityMapper.toAccountUser(userFromDB)
                    } else {
                        Log.d(Constants.USER_FROM_DB, "User doesn't exist in DB: $userFromDB")
                        return@withContext Result.Failure(Exception("User doesn't exist in DB"))
                    }
                    Log.d(Constants.TOKEN_LOG, "TOKEN VALUE: ${sessionManager.fetchAuthToken()}")
                    Log.d(Constants.LOGGED_USER, "LOGGED_USER: ${loggedUser.accountUser.toString()}")
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

    override suspend fun signOut(): Result<Int> =
        withContext(Dispatchers.IO) {
            try {
                val logoutResponse = authApi.signOut()
                val responseCode = logoutResponse.code()
                return@withContext if (logoutResponse.isSuccessful) {
                    sessionManager.deleteToken()
                    Log.d(Constants.TOKEN_LOG, "TOKEN VALUE: ${sessionManager.fetchAuthToken()}")
                    loggedUser.accountUser = null
                    Log.d(Constants.LOGGED_USER, "LOGGED_USER: ${loggedUser.accountUser.toString()}")
                    Result.Success(responseCode)
                } else {
                    Result.Failure(Exception("$responseCode code"))
                }
            } catch (e: Exception) {
                return@withContext Result.Failure(e)
            }
        }
}
