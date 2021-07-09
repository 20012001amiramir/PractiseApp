package com.example.practiseapp.domain.usecases.ProfileUseCases

import com.example.practiseapp.data.di.qualifiers.UserRepositoryMain
import com.example.practiseapp.domain.repositories.IUserRepository
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(
    @UserRepositoryMain private val userRepository: IUserRepository
): ISaveImageUseCase {

    override suspend fun invoke(uri: String) = userRepository.saveImage(uri)
}
