package com.example.practiseapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {
    private var gson = Gson()

    @TypeConverter
    fun stringToSomeObject(data: String?): Calendar? {
        if (data == null) {
            return null
        }
        val type: Type = object : TypeToken<Calendar?>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun someObjectToString(someObjects: Calendar?): String {
        return gson.toJson(someObjects)
    }


}
