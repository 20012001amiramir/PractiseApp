package com.example.practiseapp.data.di

import com.example.practiseapp.data.di.qualifiers.*
import com.example.practiseapp.domain.usecases.AuthUseCases.*
import com.example.practiseapp.domain.usecases.ProfileUseCases.GetUserUseCase
import com.example.practiseapp.domain.usecases.ProfileUseCases.IGetUserUseCase
import com.example.practiseapp.domain.usecases.ProfileUseCases.ISaveImageUseCase
import com.example.practiseapp.domain.usecases.ProfileUseCases.SaveImageUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @SaveImageUseCaseMain
    @Singleton
    @Binds
    abstract fun provideSaveImageUseCaseMain(
        saveImageUseCase: SaveImageUseCase
    ): ISaveImageUseCase

    @GetUserUseCaseMain
    @Singleton
    @Binds
    abstract fun provideGetUserUseCaseMain(
        getUserUseCase: GetUserUseCase
    ): IGetUserUseCase

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
