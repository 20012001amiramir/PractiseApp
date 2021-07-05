package com.example.practiseapp.data.bluetooth

import android.bluetooth.BluetoothDevice
import android.content.Context
import com.example.practiseapp.data.bluetooth.request.*


class BluetoothNetwork (val context: Context){

    private var eventListener : IBluetoothEventListener = BluetoothEventListener()
    private val enableRequest = EnableRequest(context, eventListener)
    private val discoverRequest = DiscoverRequest(context, eventListener)
    private val pairRequest = PairRequest(context, eventListener)
    private val connectionRequest = ConnectionRequest(context, eventListener)
    private val audioConnectionRequest = AudioConnectionRequest(context, eventListener)

    fun setBluetoothEventListener(listener: IBluetoothEventListener) {
        eventListener = listener
    }

    fun enableBluetoothAdapter() {
        enableRequest.enableBluetooth()
    }

    fun disableBluetoothAdapter() {
        enableRequest.disableBluetooth()
    }

    fun discoverDevices() {
        discoverRequest.discover()
    }

    fun pairDevice(device : BluetoothDevice) {
        pairRequest.pair(device)
    }

    fun connectDevice(device: BluetoothDevice) {
        connectionRequest.conntect(device)
    }

    fun stopConnectDevice() {
        connectionRequest.stopConnect()
    }

    fun connectAudioDevice(device : BluetoothDevice) {
        audioConnectionRequest.connect(device)
    }

    fun cleanUp() {
        enableRequest.cleanup()
        discoverRequest.cleanup()
        pairRequest.cleanup()
    }

}