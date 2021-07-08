package com.example.practiseapp.data.di

import com.example.practiseapp.data.di.qualifiers.AuthRepositoryMain
import com.example.practiseapp.data.di.qualifiers.TempRepositoryMain
import com.example.practiseapp.data.repositories.AuthRepository
import com.example.practiseapp.data.repositories.TempRepository
import com.example.practiseapp.domain.repositories.IAuthRepository
import com.example.practiseapp.domain.repositories.ITempRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @AuthRepositoryMain
    @Singleton
    @Binds
    abstract fun provideAuthRepositoryMain(
        authRepository: AuthRepository
    ): IAuthRepository

    @TempRepositoryMain
    @Singleton
    @Binds
    abstract fun provideTempRepositoryMain(
        tempRepository: TempRepository
    ): ITempRepository
}
