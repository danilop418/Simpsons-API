package com.example.simpsons.features.simpsons.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.simpsons.R
import com.example.simpsons.features.simpsons.data.SimpsonsDataRepository
import com.example.simpsons.features.simpsons.data.core.api.ApiClient
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.domain.GetSimpsonByIdUseCase

class SimpsonsDetailActivity : AppCompatActivity() {
    private val viewModel = SimpsonsDetailViewModel(
        GetSimpsonByIdUseCase(
            SimpsonsDataRepository(
                SimpsonsApiRemoteDataSource(ApiClient())
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_simpson_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val simpsonId = intent.getStringExtra("SIMPSON_ID") ?: ""

        setupObserver()
        viewModel.loadSimpson(simpsonId)
    }

    private fun setupObserver() {
        val observer = Observer<SimpsonsDetailViewModel.UiState> { uiState ->
            if (uiState.isLoading) {
                Log.d("@dev", "cargando detalle")
            }

            uiState.error?.let { error ->
                Log.d("@dev", error.toString())
            }

            uiState.simpson?.let { simpson ->
                Log.d("@dev", "Personaje detalle: ${simpson.name}")
            }
        }
        viewModel.uiState.observe(this, observer)
    }
}