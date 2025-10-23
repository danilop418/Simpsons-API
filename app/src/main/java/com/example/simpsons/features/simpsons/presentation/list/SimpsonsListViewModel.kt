package com.example.simpsons.features.simpsons.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpsons.features.simpsons.domain.ErrorApp
import com.example.simpsons.features.simpsons.domain.FetchSimpsonsUseCase
import com.example.simpsons.features.simpsons.domain.Simpson
import kotlinx.coroutines.launch
class SimpsonsListViewModel(val getAll: FetchSimpsonsUseCase):ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun loadSimpsons() {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            getAll().fold({ onSuccess(it) }, { onError(it as ErrorApp) })
        }
    }

    private fun onSuccess(simpsons: List<Simpson>) {
        _uiState.value = UiState(simpsons = simpsons)
    }

    private fun onError(error: ErrorApp) {
        _uiState.value = UiState(error = error)
    }

    data class UiState(
        val error: ErrorApp? = null,
        val isLoading: Boolean = false,
        val simpsons: List<Simpson>? = null
    )

