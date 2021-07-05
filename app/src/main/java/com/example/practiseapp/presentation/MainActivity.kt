package com.example.practiseapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practiseapp.R
import com.example.practiseapp.data.bluetooth.BluetoothNetwork
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val bluetoothConnectionService : BluetoothNetwork = BluetoothNetwork(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
