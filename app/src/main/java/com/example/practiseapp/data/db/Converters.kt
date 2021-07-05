package com.example.practiseapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {
//    private var gson = Gson()
//
//    @TypeConverter
//    fun stringToSomeObjectList(data: String?): List<com.project.data.entities.event.EventEntity.EventResult> {
//        if (data == null) {
//            return Collections.emptyList()
//        }
//        val listType: Type = object : TypeToken<List<com.project.data.entities.event.EventEntity.EventResult?>?>() {}.type
//        return gson.fromJson(data, listType)
//    }
//
//    @TypeConverter
//    fun someObjectListToString(someObjects: List<com.project.data.entities.event.EventEntity.EventResult?>?): String {
//        return gson.toJson(someObjects)
//    }
//    @TypeConverter
//    fun eventResultToString(value: EventResult): String {
//        return "${value.id}-${value.description}-${value.title}"
//    }
//
//    @TypeConverter
//    fun stringToEventResult(r: String): EventResult {
//        return EventResult(r.split(',')[0].toInt(),r.split(',')[1],r.split(',')[2])
//    }
//
//    @TypeConverter
//    fun listToJson(value: List<EventResult>?): String {
//        return Gson().toJson(value)
//    }
//
//    @TypeConverter
//    fun jsonToList(value: String): List<EventResult>? {
//        val objects = Gson().fromJson(value, Array<EventResult>::class.java) as Array<EventResult>
//        val list = objects.toList()
//        return list
//    }
}
