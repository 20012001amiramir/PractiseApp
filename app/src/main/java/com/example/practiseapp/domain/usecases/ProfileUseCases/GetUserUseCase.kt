package com.example.practiseapp.domain.usecases.ProfileUseCases

import com.example.practiseapp.data.di.qualifiers.UserRepositoryMain
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.repositories.IUserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    @UserRepositoryMain private val userRepository: IUserRepository
): IGetUserUseCase {
    override suspend fun invoke(): Result<AccountUser> = userRepository.getUser()
}
