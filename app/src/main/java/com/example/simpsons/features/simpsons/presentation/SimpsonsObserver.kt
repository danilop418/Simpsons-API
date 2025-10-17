package com.example.simpsons.features.simpsons.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpsons.features.simpsons.domain.Simpson

object SimpsonObserver {
    private val _selectedSimpson = MutableLiveData<Simpson?>()
    val selectedSimpson: LiveData<Simpson?> get() = _selectedSimpson

    fun setSimpson(simpson: Simpson) {
        _selectedSimpson.value = simpson
    }

    fun clear() {
        _selectedSimpson.value = null
    }
}