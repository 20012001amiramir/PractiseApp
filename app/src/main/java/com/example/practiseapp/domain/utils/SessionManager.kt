package com.example.practiseapp.domain.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.practiseapp.Constants

class SessionManager constructor(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(Constants.TOKEN_PREFS, Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(Constants.TOKEN_VALUE, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(Constants.TOKEN_VALUE, null)
    }

    fun deleteToken() {
        val editor = prefs.edit()
        editor.remove(Constants.TOKEN_VALUE)
        editor.apply()
    }

    fun saveLoggedUserId(id: Long) {
        val editor = prefs.edit()
        editor.putLong(Constants.LOGGED_USER_ID, id)
        editor.apply()
    }

    fun fetchLoggedUserId(): Long {
        return prefs.getLong(Constants.LOGGED_USER_ID, -1)
    }

    fun deleteLoggedUserId() {
        val editor = prefs.edit()
        editor.remove(Constants.LOGGED_USER_ID)
        editor.apply()
    }
}
