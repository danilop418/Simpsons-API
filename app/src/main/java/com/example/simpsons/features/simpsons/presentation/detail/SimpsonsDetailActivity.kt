package com.example.simpsons.features.simpsons.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpsons.features.simpsons.domain.ErrorApp
import com.example.simpsons.features.simpsons.domain.GetSimpsonByIdUseCase
import com.example.simpsons.features.simpsons.domain.Simpson
import kotlinx.coroutines.launch

class SimpsonsDetailActivity(
    private val simpsonId: String,
    private val getSimpsonByIdUseCase: GetSimpsonByIdUseCase
) : ViewModel() {

    private val _simpson = MutableLiveData<Simpson?>()
    val simpson: LiveData<Simpson?> = _simpson

    private val _error = MutableLiveData<ErrorApp?>()
    val error: LiveData<ErrorApp?> = _error

    init {
        loadSimpson()
    }

    private fun loadSimpson() {
        viewModelScope.launch {
            val result = getSimpsonByIdUseCase.invoke(simpsonId)
            result.fold(
                onSuccess = { simpson ->
                    _simpson.postValue(simpson)
                    _error.postValue(null)
                },
                onFailure = { exception ->
                    _error.postValue(exception as? ErrorApp ?: ErrorApp.ServerErrorApp)
                    _simpson.postValue(null)
                }
            )
        }
    }
}