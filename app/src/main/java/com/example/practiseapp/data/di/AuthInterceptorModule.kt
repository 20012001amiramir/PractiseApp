package com.example.practiseapp.data.di

import com.example.practiseapp.data.di.qualifiers.AuthInterceptorMain
import com.example.practiseapp.data.network.interceptors.AuthInterceptor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor

@InstallIn(SingletonComponent::class)
@Module
abstract class AuthInterceptorModule {
    @AuthInterceptorMain
    @Binds
    abstract fun bindAuthInterceptor(basicAuthInterceptor: AuthInterceptor): Interceptor
}
