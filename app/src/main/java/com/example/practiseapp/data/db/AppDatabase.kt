package com.example.practiseapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.practiseapp.data.db.temp.TempDao
import com.example.practiseapp.data.db.user.UserDao
import com.example.practiseapp.data.entities.temp.TempEntity
import com.example.practiseapp.data.entities.user.UserEntity

@Database(entities = [TempEntity::class, UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tempDao(): TempDao
    abstract fun userDao() : UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(appContext: Context): AppDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext, AppDatabase::class.java,
                    AppDatabase::class.simpleName!!
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
