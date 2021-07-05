package com.example.practiseapp.domain.repositories

import com.example.practiseapp.data.network.dto.*
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser

interface IAuthRepository {

    suspend fun signIn(accountUser: AccountUser): Result<String>

    suspend fun signUp(accountUser: AccountUser): Result<AccountUser>

    suspend fun signOut(): Result<LogoutStatus>
}
