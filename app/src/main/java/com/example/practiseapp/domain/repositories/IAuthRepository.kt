package com.example.practiseapp.domain.repositories

import com.example.practiseapp.domain.entities.AccountUser

interface IAuthRepository {

    suspend fun signIn(accountUser: AccountUser): String

    suspend fun signUp(accountUser: AccountUser): AccountUser

    suspend fun signOut(): Unit
}
