package com.example.simpsons.features.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.simpsons.features.domain.Simpson
import com.example.simpsons.features.domain.usecase.GetSimpsonsPagedUseCase
import kotlinx.coroutines.flow.Flow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SimpsonsListViewModel(
    private val getSimpsonsPagedUseCase: GetSimpsonsPagedUseCase
) : ViewModel() {

    val simpsonsPagingData: Flow<PagingData<Simpson>> = getSimpsonsPagedUseCase()
        .cachedIn(viewModelScope)
}
