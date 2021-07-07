package com.example.practiseapp.presentation.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.practiseapp.R
import com.example.practiseapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.welie.blessed.BluetoothCentralManager
import com.welie.blessed.ConnectionFailedException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private val BATTERY_SERVICE_UUID: UUID = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb")
    private val DEVICE_SERVICE_UUID: UUID = UUID.fromString("0000180A-0000-1000-8000-00805f9b34fb")
    private var arrayUUI = ArrayList<UUID>()
    private lateinit var central: BluetoothCentralManager
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bnv_main)
            .setupWithNavController(navController)

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
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
