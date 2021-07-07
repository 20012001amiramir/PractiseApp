package com.example.practiseapp.presentation.main

import androidx.lifecycle.ViewModel
import com.example.practiseapp.data.di.qualifiers.SignOutUseCaseMain
import com.example.practiseapp.domain.usecases.AuthUseCases.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @SignOutUseCaseMain private val signOutUseCase: SignOutUseCase
): ViewModel() {

}
