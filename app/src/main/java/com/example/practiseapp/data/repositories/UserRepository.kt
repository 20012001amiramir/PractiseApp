package com.example.practiseapp.data.repositories

import android.util.Log
import com.example.practiseapp.Constants
import com.example.practiseapp.data.db.user.UserDao
import com.example.practiseapp.data.mappers.UserEntityMapper
import com.example.practiseapp.data.network.UserApi
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.entities.ImageManager
import com.example.practiseapp.domain.entities.LoggedUser
import com.example.practiseapp.domain.repositories.IUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val loggedUser: LoggedUser,
    private val imageManager: ImageManager,
    private val userDao: UserDao,
    private val userApi: UserApi
): IUserRepository {

    override suspend fun getUser(): Result<AccountUser> =
        withContext(Dispatchers.IO) {
            Log.d(Constants.LOGGED_USER, "LOGGED_USER while profile: $loggedUser")
            try {
                return@withContext if (loggedUser.accountUser != null) {
                    Log.d(Constants.LOGGED_USER, "LOGGED_USER returned from loggedUser object while profile: $loggedUser")
                    val accountUser = loggedUser.accountUser
                    val imageURI = imageManager.fetchImageURI(accountUser!!.id.toLong())
                    accountUser.imageURI = imageURI
                    return@withContext Result.Success(accountUser)
                } else {
                    //  val userResponse = userApi.getUser()
                    //  if (userResponse.isSuccessful) {
                    //  Log.d(Constants.LOGGED_USER, "LOGGED_USER returned from API while profile: $loggedUser")
                    //  val accountUser = AccountUserApiMapper.toUser(userResponse.body()!!)
                        val imageURI = imageManager.fetchImageURI(accountUser.id.toLong())
                        accountUser.imageURI = imageURI
                        loggedUser.accountUser = accountUser
                        userDao.insertUser(UserEntityMapper.toUserEntity(accountUser))
                        Result.Success(accountUser)
                    // } else {
                    //   Log.d(Constants.LOGGED_USER, "LOGGED_USER didn't return: $loggedUser")
                    //   Result.Failure(Exception(userResponse.message()))
                    // }
                }
            } catch (e: Exception) {
                Log.d(Constants.LOGGED_USER, "LOGGED_USER Fail: ${e.message}")
                return@withContext Result.Failure(e)
            }
        }

    override suspend fun saveImage(uri: String) =
        withContext(Dispatchers.IO) {
            val user = loggedUser.accountUser
            if (user != null) {
                imageManager.saveImageURI(uri, user.id.toLong())
                val URI = imageManager.fetchImageURI(user.id.toLong())
                Log.d(Constants.IMAGES_LOG, "IMAGES_LOG: $URI")
            }
        }
}
