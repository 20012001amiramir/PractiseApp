package com.example.practiseapp.data.di

import com.example.practiseapp.data.di.qualifiers.SignInUseCaseMain
import com.example.practiseapp.data.di.qualifiers.SignUpUseCaseMain
import com.example.practiseapp.domain.usecases.AuthUseCases.ISignInUseCase
import com.example.practiseapp.domain.usecases.AuthUseCases.ISignUpUseCase
import com.example.practiseapp.domain.usecases.AuthUseCases.SignInUseCase
import com.example.practiseapp.domain.usecases.AuthUseCases.SignUpUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @SignInUseCaseMain
    @Singleton
    @Binds
    abstract fun provideSignInUseCaseMain(
        signInUseCase: SignInUseCase
    ): ISignInUseCase

    @SignUpUseCaseMain
    @Singleton
    @Binds
    abstract fun provideSignUpUseCaseMain(
        signUpUseCase: SignUpUseCase
    ): ISignUpUseCase
}
