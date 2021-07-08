package com.example.practiseapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiseapp.data.di.qualifiers.TempUseCaseMain
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.Temp
import com.example.practiseapp.domain.usecases.tempUseCases.ITempUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @TempUseCaseMain private val tempUseCase: ITempUseCase
    ): ViewModel() {

    private val _temps = MutableLiveData<List<Temp>>()
    val temps = _temps

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var _remoteTemp = arrayListOf<Temp>()
    fun getTemps(temp: Temp) {
        viewModelScope.launch {
            when (val tempResult = tempUseCase.invoke(temp)) {
                is Result.Success -> {
                    _remoteTemp.clear()
                    _remoteTemp.addAll(tempResult.data)

                    val tempsFlow = tempUseCase.invoke(temp)
                    tempsFlow.collect { tempz ->
                        temps.value = tempz
                    }
                }

                is kotlin.Result.Error -> {
                    _dataLoading.postValue(false)
                    categories.value = emptyList()
                    _error.postValue(categoryResult.exception.message)
                }
            }
        }
    }
}
