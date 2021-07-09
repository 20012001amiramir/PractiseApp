package com.example.practiseapp.data.repositories

import android.util.Log
import com.example.practiseapp.Constants
import com.example.practiseapp.data.db.user.UserDao
import com.example.practiseapp.data.mappers.UserEntityMapper
import com.example.practiseapp.data.network.UserApi
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.entities.LoggedUser
import com.example.practiseapp.domain.repositories.IUserRepository
import com.example.practiseapp.domain.utils.ImageManager
import com.example.practiseapp.domain.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val loggedUser: LoggedUser,
    private val imageManager: ImageManager,
    private val userDao: UserDao,
    private val userApi: UserApi,
    private val sessionManager: SessionManager
): IUserRepository {

    override suspend fun getUser(): Result<AccountUser> =
        withContext(Dispatchers.IO) {
            val accountUser: AccountUser
            try {
                // If LoggedUser object is not null, we just return this object
                return@withContext if (loggedUser.accountUser != null) {
                    Log.d(
                        Constants.LOGGED_USER,
                        "LOGGED_USER returned from loggedUser object while profile: ${loggedUser.accountUser}"
                    )
                    accountUser = loggedUser.accountUser!!
                    val imageURI = imageManager.fetchImageURI(accountUser.id.toLong())
                    accountUser.imageURI = imageURI
                    Result.Success(accountUser)
                } else if (sessionManager.fetchLoggedUserId() > -1) {  // if LoggedUser object is  null, but we have userId in SharedPrefs
                    val userId = sessionManager.fetchLoggedUserId()

                    val userFromDB = userDao.getUser(userId)
                    if (userFromDB != null) {
                        val imageURI = imageManager.fetchImageURI(userId)
                        accountUser = UserEntityMapper.toAccountUser(userFromDB)
                        accountUser.imageURI = imageURI
                        loggedUser.accountUser = accountUser
                        Log.d(
                            Constants.LOGGED_USER,
                            "LOGGED_USER returned from DB while profile, due to userId in SharedPrefs: ${loggedUser.accountUser}"
                        )
                        Log.d(
                            Constants.IMAGES_LOG,
                            "Image Uri: $imageURI"
                        )
                        Result.Success(accountUser)
                    } else {   // If user has been returned from DB as null, we create Api request to get user data
                        val userResponse = userApi.getUser(userId)
                        if (userResponse.isSuccessful && userResponse.body() != null) {
                            val userIdFromDB =
                                userDao.insertUser(UserEntityMapper.toUserEntity(userResponse.body()!!))
                            val newUserFromDB = userDao.getUser(userId)
                            if (newUserFromDB != null) {
                                val imageURI = imageManager.fetchImageURI(userIdFromDB)
                                accountUser = UserEntityMapper.toAccountUser(newUserFromDB)
                                accountUser.imageURI = imageURI
                                loggedUser.accountUser = accountUser
                                Log.d(
                                    Constants.LOGGED_USER,
                                    "LOGGED_USER returned from DB after api request, due to userId in SharedPrefs: ${loggedUser.accountUser}"
                                )
                                Log.d(
                                    Constants.IMAGES_LOG,
                                    "Image Uri: $imageURI"
                                )
                                Result.Success(accountUser)
                            }
                            Result.Failure(Exception("DB works incorrect"))
                        } else {
                            Result.Failure(Exception("Response is not successful or body is null"))
                        }
                    }
                } else {
                    Result.Failure(Exception("There is not a userId in SharedPrefs"))
                }
            } catch (e: Exception) {
                Log.d(Constants.LOGGED_USER, "getUser() failed because of Exception: ${e.message}")
                return@withContext Result.Failure(e)
            }
        }

    override suspend fun saveImage(uri: String) =
        withContext(Dispatchers.IO) {
            val user = loggedUser.accountUser
            if (user != null) {
                imageManager.saveImageURI(uri, user.id.toLong())
                val returnedUri = imageManager.fetchImageURI(user.id.toLong())
                loggedUser.accountUser!!.imageURI = returnedUri
                Log.d(Constants.IMAGES_LOG, "Uri from SharedPrefs: $returnedUri")
            }
        }
}
