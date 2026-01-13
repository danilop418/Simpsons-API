package com.example.simpsons.features.presentation.list

import SimpsonWithFavorite
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpsons.core.data.local.xml.XmlCacheStorage
import com.example.simpsons.core.domain.ErrorApp
import com.example.simpsons.features.data.local.xml.FavoriteXmlModel
import com.example.simpsons.features.domain.usecases.FetchSimpsonsUseCase
import com.example.simpsons.features.domain.Simpson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Named

@KoinViewModel
class SimpsonsListViewModel(
    val getAll: FetchSimpsonsUseCase,
    @Named("favorites") private val favoritesStorage: XmlCacheStorage<FavoriteXmlModel>
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private var allSimpsons: List<Simpson> = emptyList()
    private var favoriteIds: Set<String> = emptySet()
    private var showOnlyFavorites: Boolean = false

    fun loadSimpsons() {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            loadFavorites()
            getAll().fold({ onSuccess(it) }, { onError(it as ErrorApp) })
        }
    }

    private suspend fun loadFavorites() {
        withContext(Dispatchers.IO) {
            favoritesStorage.obtainAll().onSuccess { favorites ->
                favoriteIds = favorites.map { it.favoriteId }.toSet()
            }
        }
    }

    private fun onSuccess(simpsons: List<Simpson>) {
        allSimpsons = simpsons
        emitCombinedList()
    }

    private fun emitCombinedList() {
        val combined = allSimpsons.map { simpson ->
            SimpsonWithFavorite(simpson, favoriteIds.contains(simpson.id))
        }
        val filtered = if (showOnlyFavorites) {
            combined.filter { it.isFavorite }
        } else {
            combined
        }
        _uiState.value = UiState(simpsons = filtered, showOnlyFavorites = showOnlyFavorites)
    }

    private fun onError(error: ErrorApp) {
        _uiState.value = UiState(error = error)
    }

    fun toggleFavorite(simpson: Simpson) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val isFavorite = favoriteIds.contains(simpson.id)
                if (isFavorite) {
                    favoriteIds = favoriteIds - simpson.id
                    favoritesStorage.delete(simpson.id)
                } else {
                    favoriteIds = favoriteIds + simpson.id
                    favoritesStorage.save(FavoriteXmlModel(favoriteId = simpson.id))
                }
            }
            emitCombinedList()
        }
    }

    fun setFavoritesFilter(showFavorites: Boolean) {
        showOnlyFavorites = showFavorites
        emitCombinedList()
    }

    data class UiState(
        val error: ErrorApp? = null,
        val isLoading: Boolean = false,
        val simpsons: List<SimpsonWithFavorite>? = null,
        val showOnlyFavorites: Boolean = false
    )
}
