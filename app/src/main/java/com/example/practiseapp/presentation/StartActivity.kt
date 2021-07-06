package com.example.practiseapp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.welie.blessed.BluetoothCentralManager
import com.welie.blessed.ConnectionFailedException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import com.example.practiseapp.Constants
import com.example.practiseapp.domain.entities.SessionManager
import com.example.practiseapp.presentation.main.MainActivity
import com.example.practiseapp.presentation.welcome.WelcomeActivity
import javax.inject.Inject

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    private val BATTERY_SERVICE_UUID: UUID = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb")
    private val DEVICE_SERVICE_UUID: UUID = UUID.fromString("0000180A-0000-1000-8000-00805f9b34fb")
    private var arrayUUI = ArrayList<UUID>()
    private lateinit var central: BluetoothCentralManager
    val scope = CoroutineScope(Dispatchers.IO)

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

        central = BluetoothCentralManager(this)
        central.scanForPeripheralsWithServices(
            arrayOf(
                BATTERY_SERVICE_UUID,
                DEVICE_SERVICE_UUID
            )
        ) { peripheral, scanResult ->
            central.stopScan()
            scope.launch {
                try {
                    Log.println(Log.ASSERT, "name", peripheral.name)
                    Log.println(Log.ASSERT, "rssi", scanResult.rssi.toString())
                    Log.e("success", "connection good!")
                } catch (connectionFailed: ConnectionFailedException) {
                    Log.e("error", "connection failed(")
                }
            }

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