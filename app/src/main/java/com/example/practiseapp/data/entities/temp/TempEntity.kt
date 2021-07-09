package com.example.practiseapp.data.entities.temp

import androidx.room.*
import java.util.*

@Entity
class TempEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: Calendar,
    val temp: Double
)
