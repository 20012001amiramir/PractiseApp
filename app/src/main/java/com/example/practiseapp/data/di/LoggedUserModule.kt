package com.example.practiseapp.data.di

import com.example.practiseapp.domain.entities.LoggedUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoggedUserModule {
    @Provides
    @Singleton
    fun provideAccountUser(): LoggedUser {
        return LoggedUser()
    }
}
