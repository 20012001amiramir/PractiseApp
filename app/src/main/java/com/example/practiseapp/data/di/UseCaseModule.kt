package com.example.practiseapp.data.di

import com.example.practiseapp.data.di.qualifiers.SignInUseCaseMain
import com.example.practiseapp.data.di.qualifiers.SignOutUseCaseMain
import com.example.practiseapp.data.di.qualifiers.SignUpUseCaseMain
import com.example.practiseapp.domain.usecases.AuthUseCases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @SignOutUseCaseMain
    @Singleton
    @Binds
    abstract fun provideSignOutUseCaseMain(
        signOutUseCase: SignOutUseCase
    ): ISignOutUseCase

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
