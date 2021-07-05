package com.example.practiseapp.presentation.welcome

import androidx.lifecycle.*
import com.example.practiseapp.data.di.qualifiers.SignInUseCaseMain
import com.example.practiseapp.data.di.qualifiers.SignUpUseCaseMain
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.usecases.AuthUseCases.ISignInUseCase
import com.example.practiseapp.domain.usecases.AuthUseCases.ISignUpUseCase
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    @SignInUseCaseMain private val signInUseCase: ISignInUseCase,
    @SignUpUseCaseMain private val signUpUseCase: ISignUpUseCase
): ViewModel(){
    private val _isAuthenticated = MutableLiveData<Result<Boolean>>()
    val isAuthenticated: LiveData<Result<Boolean>>
        get() = _isAuthenticated

    fun signIn(accountUser: AccountUser) = viewModelScope.launch {
        _isAuthenticated.value = signInUseCase(accountUser)
    }

    fun signUp(accountUser: AccountUser) = viewModelScope.launch {
        _isAuthenticated.value = signUpUseCase(accountUser)
    }
}
