package com.example.practiseapp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.practiseapp.Constants
import com.example.practiseapp.domain.entities.SessionManager
import com.example.practiseapp.presentation.main.MainActivity
import com.example.practiseapp.presentation.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constants.TOKEN_LOG, "TOKEN VALUE: ${sessionManager.fetchAuthToken()}")
        if (sessionManager.fetchAuthToken() != null) {
            MainActivity.start(this)
            finish()
        } else {
            WelcomeActivity.start(this)
            finish()
        }
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, StartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
