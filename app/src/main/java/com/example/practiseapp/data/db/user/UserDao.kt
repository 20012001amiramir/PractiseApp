package com.example.practiseapp.data.db.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practiseapp.data.entities.user.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: Long): UserEntity?

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUser(id: Long): Int
}
