package com.example.practiseapp.data.repositories

import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.repositories.IAuthRepository
import com.example.practiseapp.data.network.AuthApi
import com.example.practiseapp.data.network.dto.LogoutStatus
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.data.network.mappers.AccountUserApiMapper
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) : IAuthRepository {

    override suspend fun signIn(accountUser: AccountUser): Result<String> {
        val userResponse = authApi.signIn(
            AccountUserApiMapper.toUserSignInDto(accountUser)
        )
        return if (userResponse.isSuccessful) {
            Result.Success(userResponse.body()!!.token)
        } else {
            Result.Failure(Exception(userResponse.message()))
        }
    }

    override suspend fun signUp(accountUser: AccountUser): Result<AccountUser> {
       val userResponse = authApi.signUp(
           AccountUserApiMapper.toUserSignUpDto(accountUser)
       )
        return if (userResponse.isSuccessful) {
            Result.Success(AccountUserApiMapper.toUser(userResponse.body()!!))
        } else {
            Result.Failure(Exception(userResponse.message()))
        }

    }

    override suspend fun signOut(): Result<LogoutStatus> {
        TODO("Not yet implemented")
    }
    //TODO:Rewrite with exceptions
}
