package com.example.practiseapp.data.di

import com.example.practiseapp.data.di.qualifiers.AuthRepositoryMain
import com.example.practiseapp.data.di.qualifiers.UserRepositoryMain
import com.example.practiseapp.data.repositories.AuthRepository
import com.example.practiseapp.data.repositories.UserRepository
import com.example.practiseapp.domain.repositories.IAuthRepository
import com.example.practiseapp.domain.repositories.IUserRepository
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

    @UserRepositoryMain
    @Singleton
    @Binds
    abstract fun provideUserRepositoryMain(
        userRepository: UserRepository
    ): IUserRepository
}
