package com.example.practiseapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiseapp.data.di.qualifiers.SignOutUseCaseMain
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.utils.SessionManager
import com.example.practiseapp.domain.usecases.AuthUseCases.ISignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @SignOutUseCaseMain private val signOutUseCase: ISignOutUseCase,
    private val sessionManager: SessionManager
): ViewModel() {

    private val _logoutStatus = MutableLiveData<Result<Int>>()
    val logoutStatus: LiveData<Result<Int>>
        get() = _logoutStatus

    fun signOut() = viewModelScope.launch {
        _logoutStatus.postValue(signOutUseCase()!!)
    }

    fun deleteToken() {
        sessionManager.deleteToken()
    }
}
