package com.example.practiseapp.features.bluetooth

import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothGatt
import android.util.Log
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
import java.time.LocalTime

@SuppressLint("LogNotTimber")
class BluetoothServer(application: Application) {
    private lateinit var devices: MutableList<BleDevice>
    private var _app = application
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
            }

            override fun onScanning(bleDevice: BleDevice?) {
                Log.d("appconfig","${bleDevice?.name} - ${bleDevice?.mac}")
            }

            override fun onScanFinished(scanResultList: MutableList<BleDevice>?) {
                scanResultList?.let {
                    devices = it

                }
                startConnection(ble, scanResultList)
                Log.d("appconfig","devices: ${scanResultList?.toString() ?: ""}")
            }

            fun startConnection(ble: BleManager, devices: MutableList<BleDevice>?) {
                ble.connect("FA:E6:41:AF:56:30", object : BleGattCallback() {
                    override fun onStartConnect() {
                        Log.d("appconfig","Start connnect!")
                    }

                    override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                        Log.d("appconfig","Fail!")
                    }

                    override fun onConnectSuccess(
                        bleDevice: BleDevice?,
                        gatt: BluetoothGatt?,
                        status: Int
                    ) {
                        Log.d("appconfig","Connect!")
                        Log.d("appconfig",bleDevice?.device?.address?.toString() ?: "empty")
                        gatt?.let { transferData(it) }
                        Log.d("appconfig","")
                        ble.read(
                            bleDevice!!,
                            MEASURE_SERVICE_UUID,
                            TEMP_CHAR_UUID,
                            object : BleReadCallback() {
                                override fun onReadSuccess(data: ByteArray) {
                                    Log.d("appconfig","bytes: $data")
                                }

                                override fun onReadFailure(exception: BleException) {
                                    Log.d("appconfig","bytes: failure")
                                    Log.d("appconfig",exception.toString() + "\n" + exception.description)
                                }
                            }
                        )
                        ble.notify(
                            bleDevice,
                            MEASURE_SERVICE_UUID,
                            TEMP_CHAR_UUID,
                            object : BleNotifyCallback() {
                                override fun onNotifySuccess() {
                                    Log.d("appconfig","notify success")
                                }

                                override fun onNotifyFailure(exception: BleException?) {
                                    Log.d("appconfig","failure")
                                }
                                fun toBinary(value: Int): String{
                                    var number = value
                                    if(value < 0) number+=255
                                    return String.format("%8s",Integer.toBinaryString(number)).replace(" ","0")
                                }
                                fun binaryToInt(binaryDigits: String): Int {
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
                                fun toInteger(array: Array<Byte>): String{
                                    var str = ""
                                    array.forEach { x ->
                                        str += toBinary(x.toInt())
                                    }
                                    return str
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
                        Log.d("appconfig","Disconnect!")
                    }

                })
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