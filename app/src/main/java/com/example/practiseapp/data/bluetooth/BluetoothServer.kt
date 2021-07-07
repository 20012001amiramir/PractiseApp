package com.example.practiseapp.data.bluetooth

import android.app.Application
import android.bluetooth.BluetoothGatt
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.callback.BleNotifyCallback
import com.clj.fastble.callback.BleReadCallback
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.clj.fastble.scan.BleScanRuleConfig
import timber.log.Timber

private val MEASURE_SERVICE_UUID = "0000d03a-0000-1000-8000-00805f9b34fb"
private val TEMP_CHAR_UUID = "0000ffed-0000-1000-8000-00805f9b34fb"

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
            Timber.i("Dont support bluetooth")
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
                Timber.d("${bleDevice?.name} - ${bleDevice?.mac ?: ""}")
            }

            override fun onScanFinished(scanResultList: MutableList<BleDevice>?) {
                scanResultList?.let {
                    devices = it

                }
                startConnection(ble, scanResultList)
                Timber.d("devices: ${scanResultList?.toString() ?: ""}")
            }

            fun startConnection(ble: BleManager, devices: MutableList<BleDevice>?) {
                ble.connect(devices?.get(0), object : BleGattCallback() {
                    override fun onStartConnect() {
                        Timber.d("Start connnect!")
                    }

                    override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                        Timber.d("Fail!")
                    }

                    override fun onConnectSuccess(
                        bleDevice: BleDevice?,
                        gatt: BluetoothGatt?,
                        status: Int
                    ) {
                        Timber.d("Connect!")
                        Timber.d(bleDevice?.device?.address?.toString() ?: "empty")
                        Timber.d("")
                        ble.read(
                            bleDevice!!,
                            MEASURE_SERVICE_UUID,
                            TEMP_CHAR_UUID,
                            object : BleReadCallback() {
                                override fun onReadSuccess(data: ByteArray) {
                                    Timber.d("bytes: $data")
                                }

                                override fun onReadFailure(exception: BleException) {
                                    Timber.d("bytes: failure")
                                    Timber.d(exception.toString() + "\n" + exception.description)
                                }
                            }
                        )
                        ble.notify(
                            bleDevice,
                            MEASURE_SERVICE_UUID,
                            TEMP_CHAR_UUID,
                            object : BleNotifyCallback() {
                                override fun onNotifySuccess() {
                                    Timber.d("notify success")
                                }

                                override fun onNotifyFailure(exception: BleException?) {
                                    Timber.d("failure")
                                }

                                override fun onCharacteristicChanged(data: ByteArray?) {
                                    var output = ""
                                    var x = data!!.decodeToString(0, 2)
                                    for (byte in data) {
                                        output = "$output${byte}:"
                                    }
//                        00010010-00100001
                                    Timber.d("decoded byte array = ${data.decodeToString()}")
                                    Timber.d("notification bytes = $output")
                                    Timber.d("notification temp = ${x}")
                                }

                            })
                    }

                    override fun onDisConnected(
                        isActiveDisConnected: Boolean,
                        device: BleDevice?,
                        gatt: BluetoothGatt?,
                        status: Int
                    ) {
                        Timber.d("Disconnect!")
                    }

                })
            }
        })
    }
}