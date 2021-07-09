package com.example.practiseapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiseapp.data.di.qualifiers.GetUserUseCaseMain
import com.example.practiseapp.data.di.qualifiers.SaveImageUseCaseMain
import com.example.practiseapp.data.di.qualifiers.SignOutUseCaseMain
import com.example.practiseapp.domain.common.ConsumableValue
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.usecases.AuthUseCases.ISignOutUseCase
import com.example.practiseapp.domain.usecases.ProfileUseCases.IGetUserUseCase
import com.example.practiseapp.domain.usecases.ProfileUseCases.ISaveImageUseCase
import com.example.practiseapp.domain.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @SignOutUseCaseMain private val signOutUseCase: ISignOutUseCase,
    @GetUserUseCaseMain private val getUserUseCase: IGetUserUseCase,
    @SaveImageUseCaseMain private val saveImageUseCase: ISaveImageUseCase
): ViewModel() {

    private val _logoutStatus = MutableLiveData<ConsumableValue<Result<Int>>>()
    private val _userData = MutableLiveData<Result<AccountUser>>()
    val logoutStatus: LiveData<ConsumableValue<Result<Int>>>
        get() = _logoutStatus
    val userData: LiveData<Result<AccountUser>>
        get() = _userData

    fun saveImage(uri: String) = viewModelScope.launch {
        saveImageUseCase(uri)
    }

    fun getUser() = viewModelScope.launch {
        _userData.postValue(getUserUseCase()!!)
    }

    fun signOut() = viewModelScope.launch {
        _logoutStatus.postValue(ConsumableValue(signOutUseCase()))
    }
}
