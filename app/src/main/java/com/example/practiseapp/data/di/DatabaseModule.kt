package com.example.practiseapp.data.di

import android.content.Context
import com.example.practiseapp.data.db.AppDatabase
import com.example.practiseapp.data.db.temp.TempDao
import com.example.practiseapp.data.db.user.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Singleton
    @Provides
    fun provideTempDao(database: AppDatabase): TempDao {
        return database.tempDao()
    }
}
