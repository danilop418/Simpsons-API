package com.example.simpsons.features.simpsons.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpsons.features.simpsons.domain.ErrorApp
import com.example.simpsons.features.simpsons.domain.FetchSimpsonsUseCase
import com.example.simpsons.features.simpsons.domain.Simpson
import kotlinx.coroutines.launch

class SimpsonsListViewModel(
    private val fetchSimpsonsUseCase: FetchSimpsonsUseCase
) : ViewModel() {

    private val _simpsons = MutableLiveData<List<SimpsonUiModel>>()
    val simpsons: LiveData<List<SimpsonUiModel>> = _simpsons

    private val _error = MutableLiveData<ErrorApp?>()
    val error: LiveData<ErrorApp?> = _error

    fun loadSimpsons() {
        viewModelScope.launch {
            val result = fetchSimpsonsUseCase.fetch()
            result.fold(
                onSuccess = { simpsons ->
                    _simpsons.postValue(simpsons.map { it.toUiModel() })
                    _error.postValue(null)
                },
                onFailure = { exception ->
                    val error = when (exception) {
                        is ErrorApp.InternetConexionError -> ErrorApp.InternetConexionError
                        is ErrorApp.ServerErrorApp -> ErrorApp.ServerErrorApp
                        else -> ErrorApp.ServerErrorApp
                    }
                    _error.postValue(error)
                    _simpsons.postValue(emptyList())
                }
            )
        }
    }

    private fun Simpson.toUiModel() = SimpsonUiModel(
        id = id,
        name = name,
        phrase = phrase,
        imageUrl = imageUrl
    )
}