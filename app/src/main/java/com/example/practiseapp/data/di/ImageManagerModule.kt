package com.example.practiseapp.data.di

import android.content.Context
import com.example.practiseapp.domain.entities.ImageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ImageManagerModule {

    @Singleton
    @Provides
    fun provideImageManager(@ApplicationContext context: Context) = ImageManager(context)
}
