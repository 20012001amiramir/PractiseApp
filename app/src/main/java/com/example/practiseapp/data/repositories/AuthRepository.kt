package com.example.practiseapp.data.repositories

import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.repositories.IAuthRepository
import com.example.practiseapp.data.network.AuthApi
import com.example.practiseapp.data.network.mappers.AccountUserApiMapper
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) : IAuthRepository {

    override suspend fun signIn(accountUser: AccountUser): String {
        val userResponse = authApi.signIn(
            AccountUserApiMapper.toUserSignInDto(accountUser)
        )
        return userResponse.token
    }

    override suspend fun signUp(accountUser: AccountUser): AccountUser {
       val userResponse = authApi.signUp(
           AccountUserApiMapper.toUserSignUpDto(accountUser)
       )
       return AccountUserApiMapper.toUser(userResponse)
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }
    //TODO:Rewrite with exceptions
}
