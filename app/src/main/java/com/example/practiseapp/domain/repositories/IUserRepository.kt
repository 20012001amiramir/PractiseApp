package com.example.practiseapp.domain.repositories

import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser

interface IUserRepository {

    suspend fun getUser(): Result<AccountUser>

    suspend fun saveImage(uri: String)
}
