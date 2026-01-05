package com.example.simpsons.features.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpsons.core.errors.ErrorApp
import com.example.simpsons.features.domain.usecases.GetSimpsonByIdUseCase
import com.example.simpsons.features.domain.Simpson
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SimpsonsDetailViewModel(val getById: GetSimpsonByIdUseCase) : ViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun loadSimpson(id:String) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            getById(id).fold(
                { simpson -> onSuccess(simpson) },
                { error -> onError(error as ErrorApp) })
        }
    }

    private fun onSuccess(simpson: Simpson) {
        _uiState.value = UiState(simpson = simpson)
    }

    private fun onError(error: ErrorApp) {
        _uiState.value = UiState(error = error)
    }

    data class UiState(
        val error: ErrorApp? = null,
        val isLoading: Boolean = false,
        val simpson: Simpson? = null
    )
}