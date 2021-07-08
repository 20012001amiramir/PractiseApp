package com.example.practiseapp.features.bluetooth

import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothGatt
import android.util.Log
import android.view.View
import android.widget.Toast
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.callback.BleNotifyCallback
import com.clj.fastble.callback.BleReadCallback
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.clj.fastble.scan.BleScanRuleConfig
import com.example.practiseapp.Constants.MEASURE_SERVICE_UUID
import com.example.practiseapp.Constants.TEMP_CHAR_UUID
import com.example.practiseapp.databinding.ActivityMainBinding
import com.example.practiseapp.databinding.HomePageBinding
import java.time.LocalTime
import javax.security.auth.callback.Callback

@SuppressLint("LogNotTimber")
class BluetoothServer(application: Application, binding: HomePageBinding) {
    private lateinit var devices: MutableList<BleDevice>
    private var _app = application
    private var _binding = binding
    fun connectBluetooth() {
        val ble = BleManager.getInstance()
        ble.init(_app)
        ble.enableLog(true)
            .setReConnectCount(1, 5000)
            .setSplitWriteNum(20)
            .setConnectOverTime(10000).operateTimeout = 5000
        if (!ble.isSupportBle) {
            Log.i("appconfig","Dont support bluetooth")
            return
        } else if (!ble.isBlueEnable) {
            ble.enableBluetooth()
        }
        val scanRuleConfig = BleScanRuleConfig.Builder()
            .setDeviceMac("FA:E6:41:AF:56:30")
            .setAutoConnect(true)
            .setScanTimeOut(10000)
            .build()
        ble.initScanRule(scanRuleConfig)
        ble.scan(object : BleScanCallback() {
            override fun onScanStarted(success: Boolean) {
                _binding.CardView.visibility = View.INVISIBLE
                _binding.connectToDevice.visibility = View.INVISIBLE
                _binding.progressBar.visibility = View.VISIBLE
            }

            override fun onScanning(bleDevice: BleDevice?) {
            }

            override fun onScanFinished(scanResultList: MutableList<BleDevice>?) {
                scanResultList?.let {
                    devices = it
                }
                startConnection(ble, scanResultList,_binding)
            }

            fun startConnection(ble: BleManager, devices: MutableList<BleDevice>?,binding: HomePageBinding) {
                ble.connect("FA:E6:41:AF:56:30", object : BleGattCallback() {
                    override fun onStartConnect() {
                    }

                    override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                        Toast.makeText(_app.applicationContext,"Fail to connect!",Toast.LENGTH_SHORT).show()
                        binding.CardView.visibility = View.INVISIBLE
                        binding.connectToDevice.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.INVISIBLE
                    }

                    override fun onConnectSuccess(
                        bleDevice: BleDevice?,
                        gatt: BluetoothGatt?,
                        status: Int
                    ) {

                        gatt?.let { transferData(it) }
                        ble.notify(
                            bleDevice,
                            MEASURE_SERVICE_UUID,
                            TEMP_CHAR_UUID,
                            object : BleNotifyCallback() {
                                override fun onNotifySuccess() {
                                    binding.CardView.visibility = View.VISIBLE
                                    binding.connectToDevice.visibility = View.INVISIBLE
                                    binding.progressBar.visibility = View.INVISIBLE
                                    Toast.makeText(_app.applicationContext,"Success to connect!",Toast.LENGTH_SHORT).show()
                                }
                                override fun onNotifyFailure(exception: BleException?) {
                                }
                                override fun onCharacteristicChanged(data: ByteArray ) {
                                    val temp: Double = binaryToInt(toInteger(arrayOf(data[0],data[1]))).toDouble()/100
                                    val time: String = LocalTime.now().toString()
                                    Log.d("appconfig","notification time = $time")
                                    Log.d("appconfig","notification temp = $temp")
                                }

                            })
                    }
                    override fun onDisConnected(
                        isActiveDisConnected: Boolean,
                        device: BleDevice?,
                        gatt: BluetoothGatt?,
                        status: Int
                    ) {
                        Toast.makeText(_app.applicationContext,"Disconnect!",Toast.LENGTH_SHORT).show()
                        binding.CardView.visibility = View.INVISIBLE
                        binding.connectToDevice.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.INVISIBLE
                    }

                })
            }
            private fun toBinary(value: Int): String{
                var number = value
                if(value < 0) number+=255
                return String.format("%8s",Integer.toBinaryString(number)).replace(" ","0")
            }
            private fun binaryToInt(binaryDigits: String): Int {
                val binaryValues = arrayListOf(1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768)
                val reversedDigits = binaryDigits.reversed()

                var returnedInt = 0
                var x = 0
                for (i in reversedDigits){
                    if (i == '1' && x < 16) {
                        returnedInt += binaryValues[x]
                    }
                    x++
                }
                return returnedInt
            }
            private fun toInteger(array: Array<Byte>): String{
                var str = ""
                array.forEach { x ->
                    str += toBinary(x.toInt())
                }
                return str
            }
            private fun transferData(gatt: BluetoothGatt) {
                val serviceList = gatt.services
                for (service in serviceList) {
                    val uuid = service.uuid
                    Log.d("appconfig",uuid.toString())
                    Log.d("appconfig","uuds8::::::::::::::::::::::::::::::::::::)")
                    val characteristicList = service.characteristics
                    for (char in characteristicList) {
                        val UUID_CHAR = char.uuid
                        Log.d("appconfig","CHAR_UUID = $UUID_CHAR")
                    }

                }
            }
        })

    }
}