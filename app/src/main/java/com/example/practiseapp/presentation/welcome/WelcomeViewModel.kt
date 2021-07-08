package com.example.practiseapp.presentation.welcome

import androidx.lifecycle.*
import com.example.practiseapp.data.di.qualifiers.SignInUseCaseMain
import com.example.practiseapp.data.di.qualifiers.SignUpUseCaseMain
import com.example.practiseapp.domain.common.ConsumableValue
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.usecases.AuthUseCases.ISignInUseCase
import com.example.practiseapp.domain.usecases.AuthUseCases.ISignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    @SignInUseCaseMain private val signInUseCase: ISignInUseCase,
    @SignUpUseCaseMain private val signUpUseCase: ISignUpUseCase
): ViewModel(){
    private val _token = MutableLiveData<ConsumableValue<Result<String>>>()
    private val _userData = MutableLiveData<ConsumableValue<Result<AccountUser>>>()
    val token: LiveData<ConsumableValue<Result<String>>>
        get() = _token
    val userData: LiveData<ConsumableValue<Result<AccountUser>>>
        get() = _userData

    fun signIn(accountUser: AccountUser) = viewModelScope.launch {
        _token.postValue(ConsumableValue(signInUseCase(accountUser)))
    }

    fun signUp(accountUser: AccountUser) = viewModelScope.launch {
        _userData.postValue(ConsumableValue(signUpUseCase(accountUser)))
    }
}
