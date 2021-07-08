package com.example.practiseapp.domain.entities

import android.content.Context
import android.content.SharedPreferences
import com.example.practiseapp.Constants

class ImageManager constructor(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(Constants.IMAGES_PREFS, Context.MODE_PRIVATE)

    fun saveImageURI(imageURI: String, userId: Long) {
        val editor = prefs.edit()
        editor.putString(Constants.IMAGES_VALUE + "$userId", imageURI)
        editor.apply()
    }

    fun fetchImageURI(userId: Long): String? {
        return prefs.getString(Constants.IMAGES_VALUE + "$userId", null)
    }

    fun deleteImageURI(userId: Long) {
        val editor = prefs.edit()
        editor.remove(Constants.IMAGES_VALUE + "$userId")
        editor.apply()
    }
}
