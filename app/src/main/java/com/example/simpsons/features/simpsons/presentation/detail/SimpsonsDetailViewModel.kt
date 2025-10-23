package com.example.simpsons.features.simpsons.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simpsons.features.simpsons.domain.Simpson

class SimpsonDetailViewModel : ViewModel() {
    private val _simpson = MutableLiveData<Simpson>()
    val simpson: LiveData<Simpson> = _simpson

    fun setSimpson(simpson: Simpson?) {
        simpson?.let { _simpson.value = it }
    }
}