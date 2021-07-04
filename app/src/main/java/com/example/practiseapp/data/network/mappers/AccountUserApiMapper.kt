package com.example.practiseapp.data.network.mappers

import com.example.practiseapp.data.network.dto.UserSignInResponse
import com.example.practiseapp.data.network.dto.UserSignIn
import com.example.practiseapp.data.network.dto.UserSignUp
import com.example.practiseapp.data.network.dto.UserSignUpResponse
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
        username = userSignInResponse.data.username,
        email = userSignInResponse.data.email,
        firstName = userSignInResponse.data.firstName,
        lastName = userSignInResponse.data.lastName,
    )

    fun toUser(userSignUpResponse: UserSignUpResponse) = AccountUser(
        username = userSignUpResponse.username,
        email = userSignUpResponse.email,
        firstName = userSignUpResponse.firstName,
        lastName = userSignUpResponse.lastName,

    )
}
