package com.example.practiseapp.domain.usecases.ProfileUseCases

interface ISaveImageUseCase {
    suspend operator fun invoke(uri: String)
}
