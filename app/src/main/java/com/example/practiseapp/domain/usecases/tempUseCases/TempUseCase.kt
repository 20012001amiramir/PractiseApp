package com.example.practiseapp.domain.usecases.tempUseCases

import com.example.practiseapp.data.di.qualifiers.TempRepositoryMain
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.Temp
import com.example.practiseapp.domain.repositories.ITempRepository
import javax.inject.Inject

class TempUseCase @Inject constructor(
    @TempRepositoryMain private val tempRepository: ITempRepository
): ITempUseCase {
    override suspend fun invoke(Temp: Temp): Result<Temp> =
        tempRepository.getTemp(Temp)
}
