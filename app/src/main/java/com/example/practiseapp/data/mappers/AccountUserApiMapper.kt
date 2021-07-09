package com.example.practiseapp.data.mappers

import com.example.practiseapp.data.network.dto.*
import com.example.practiseapp.domain.entities.AccountUser

object AccountUserApiMapper {

    fun toUserSignInDto(accountUser: AccountUser) = UserSignIn(
        password = accountUser.password,
        username = accountUser.username
    )

    fun toUserSignUpDto(accountUser: AccountUser) = UserSignUp(
        email = accountUser.email,
        firstName = accountUser.firstName,
        lastName = accountUser.lastName,
        password = accountUser.password,
        username = accountUser.username
    )

    fun toUser(userSignInResponse: UserSignInResponse) = AccountUser(
        id = userSignInResponse.data.id.toString(),
        username = userSignInResponse.data.username,
        email = userSignInResponse.data.email,
        firstName = userSignInResponse.data.firstName,
        lastName = userSignInResponse.data.lastName
    )

    fun toUser(userSignUpResponse: UserSignUpResponse) = AccountUser(
        username = userSignUpResponse.username,
        email = userSignUpResponse.email,
        firstName = userSignUpResponse.firstName,
        lastName = userSignUpResponse.lastName
    )

    fun toUser(userGetResponse: UserGetResponse) = AccountUser(
        id = userGetResponse.id.toString(),
        username = userGetResponse.username,
        email = userGetResponse.email,
        firstName = userGetResponse.firstName,
        lastName = userGetResponse.lastName
    )

}
