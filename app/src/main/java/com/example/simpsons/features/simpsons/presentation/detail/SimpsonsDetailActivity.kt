package com.example.simpsons.features.simpsons.presentation.detail

import android.os.Bundle
import android.util.Log
import android.widget.TextView
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

    companion object {
        const val EXTRA_SIMPSON_ID = "extra_simpson_id"
    }

    private val viewModel = SimpsonsDetailViewModel(
        GetSimpsonByIdUseCase(
            SimpsonsDataRepository(
                SimpsonsApiRemoteDataSource(ApiClient())
            )
        )
    )

    private lateinit var nameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_simpson_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViews()
        setupObserver()
        loadSimpson()
    }

    private fun setupViews() {
        nameTextView = findViewById(R.id.simpson_name)
    }

    private fun loadSimpson() {
        val simpsonId = intent.getStringExtra(EXTRA_SIMPSON_ID)
        simpsonId?.let {
            viewModel.loadSimpson(it)
        } ?: run {
            Log.e("@dev", "No se recibió ID del personaje")
            finish()
        }
    }

    private fun setupObserver() {
        val observer = Observer<SimpsonsDetailViewModel.UiState> { uiState ->
            if (uiState.isLoading) {
                Log.d("@dev", "Cargando detalle...")
            }

            uiState.error?.let { error ->
                Log.d("@dev", "Error: ${error.toString()}")
            }

            uiState.simpson?.let { simpson ->
                Log.d("@dev", "Personaje recibido: ${simpson.name}")
                showSimpson(simpson)
            }
        }
        viewModel.uiState.observe(this, observer)
    }

    private fun showSimpson(simpson: com.example.simpsons.features.simpsons.domain.Simpson) {

    }
}