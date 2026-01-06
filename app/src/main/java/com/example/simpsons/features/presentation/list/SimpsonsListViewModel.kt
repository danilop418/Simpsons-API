package com.example.simpsons.features.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpsons.core.domain.ErrorApp
import com.example.simpsons.features.domain.usecases.FetchSimpsonsUseCase
import com.example.simpsons.features.domain.Simpson
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SimpsonsListViewModel(val getAll: FetchSimpsonsUseCase) : ViewModel() {

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
}

