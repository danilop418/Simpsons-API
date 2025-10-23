package com.example.simpsons.features.simpsons.presentation.list

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.simpsons.R
import com.example.simpsons.features.simpsons.data.SimpsonsDataRepository
import com.example.simpsons.features.simpsons.data.core.api.ApiClient
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.domain.FetchSimpsonsUseCase

class SimpsonsListActivity : AppCompatActivity() {

    private val viewModel = SimpsonsListViewModel(
        FetchSimpsonsUseCase(
            SimpsonsDataRepository(
                SimpsonsApiRemoteDataSource(ApiClient())
            )
        )
    )

    private lateinit var adapter: SimpsonsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_simpsons)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupObserver()
        viewModel.loadSimpsons()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.simpsonRecyclerView)
        adapter = SimpsonsAdapter()
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        val observer = Observer<SimpsonsListViewModel.UiState>{ uiState ->
            if (uiState.isLoading){
                Log.d("@dev", "cargando")
            }

            uiState.error?.let { error ->
                Log.d("@dev", error.toString())
            }

            uiState.simpsons?.let { characters ->
                Log.d("@dev", "Personajes recibidos: ${characters.size}")
                adapter.updateSimpsons(characters)
            }
        }
        viewModel.uiState.observe(this, observer)
    }
}