package com.example.practiseapp.data.repositories

import android.util.Log
import com.example.practiseapp.Constants
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.practiseapp.data.mappers.AccountUserApiMapper
import com.example.practiseapp.domain.entities.Temp
import com.example.practiseapp.domain.repositories.ITempRepository
import com.example.practiseapp.features.bluetooth.BluetoothServer
import javax.inject.Inject

class TempRepository @Inject constructor(
    private val bluetoothServer: BluetoothServer,
    private val temps: ArrayList<Temp>
) : ITempRepository {
    override suspend fun getTemp(Temp: Temp): Result<Temp> =
        withContext(Dispatchers.IO) {
            try {

                return@withContext if (userResponse.isSuccessful) {
                    val token = userResponse.body()!!.token
                    sessionManager.saveAuthToken(token)
                    loggedUser.accountUser = AccountUserApiMapper.toUser(userResponse.body()!!)
                    Result.Success(token)
                } else {
                    Result.Failure(Exception(userResponse.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Failure(e)
            }
        }
}
