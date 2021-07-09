package com.example.practiseapp.data.mappers

import com.example.practiseapp.data.entities.user.UserEntity
import com.example.practiseapp.data.network.dto.UserGetResponse
import com.example.practiseapp.data.network.dto.UserSignInResponse
import com.example.practiseapp.domain.entities.AccountUser

object UserEntityMapper {

    fun toUserEntity(userSignInResponse: UserSignInResponse) = UserEntity(
        id = userSignInResponse.data.id.toLong(),
        username = userSignInResponse.data.username,
        email = userSignInResponse.data.email,
        firstName = userSignInResponse.data.firstName,
        lastName = userSignInResponse.data.lastName
    )

    fun toUserEntity(userGetResponse: UserGetResponse) = UserEntity(
        id = userGetResponse.id.toLong(),
        username = userGetResponse.username,
        email = userGetResponse.email,
        firstName = userGetResponse.firstName,
        lastName = userGetResponse.lastName
    )

    fun toUserEntity(accountUser: AccountUser) = UserEntity(
        id = accountUser.id.toLong(),
        username = accountUser.username,
        email = accountUser.email,
        firstName = accountUser.firstName,
        lastName = accountUser.lastName
    )

    fun toAccountUser(userEntity: UserEntity) = AccountUser(
        id = userEntity.id.toString(),
        username = userEntity.username,
        email = userEntity.email,
        firstName = userEntity.firstName,
        lastName = userEntity.lastName
    )
}
