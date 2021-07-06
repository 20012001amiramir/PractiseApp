package com.example.practiseapp.data.db.temp

import androidx.room.*
import com.example.practiseapp.data.entities.temp.TempEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TempDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemp(temp: TempEntity)

    @Query("SELECT * from TempEntity")
    fun getAllTemp(): Flow<List<TempEntity>>

    @Delete
    suspend fun deleteTemp(temp: TempEntity)
}