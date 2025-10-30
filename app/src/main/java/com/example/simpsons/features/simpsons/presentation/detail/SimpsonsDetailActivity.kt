package com.example.simpsons.features.simpsons.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.load
import com.example.simpsons.R
import com.example.simpsons.features.simpsons.data.SimpsonsDataRepository
import com.example.simpsons.features.simpsons.data.core.api.ApiClient
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.domain.GetSimpsonByIdUseCase
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.simpsons.features.simpsons.domain.ErrorApp
import kotlin.math.max

class SimpsonsDetailActivity : AppCompatActivity() {
    private val viewModel = SimpsonsDetailViewModel(
        GetSimpsonByIdUseCase(
            SimpsonsDataRepository(
                SimpsonsApiRemoteDataSource(ApiClient())
            )
        )
    )
    private lateinit var loadingContainer: View
    private lateinit var contentScrollView: ScrollView
    private lateinit var simpsonName: TextView
    private lateinit var simpsonPhrase: TextView
    private lateinit var simpsonImage: ImageView

    private var loadingStartTime: Long = 0L

    private fun initViews() {
        loadingContainer = findViewById(R.id.loadingContainer)
        contentScrollView = findViewById(R.id.contentScrollView)
        simpsonName = findViewById(R.id.simpsonName)
        simpsonPhrase = findViewById(R.id.simpsonPhrase)
        simpsonImage = findViewById(R.id.simpsonImage)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_simpson_detail)

        val simpsonId = intent.getStringExtra("SIMPSON_ID") ?: ""

        initViews()
        setupObserver()
        viewModel.loadSimpson(simpsonId)
    }

    private fun setupObserver() {
        val observer = Observer<SimpsonsDetailViewModel.UiState> { uiState ->
            if (uiState.isLoading) {
                Log.d("@dev", "cargando detalle")
                loadingStartTime = System.currentTimeMillis()
                loadingContainer.visibility = View.VISIBLE
                contentScrollView.visibility = View.GONE
            } else {
                val elapsedTime = System.currentTimeMillis() - loadingStartTime
                val delayTime = max(0L, 3000L - elapsedTime)
                Handler(Looper.getMainLooper()).postDelayed({
                    loadingContainer.visibility = View.GONE
                    contentScrollView.visibility = View.VISIBLE
                }, delayTime)

                uiState.error?.let { error ->
                    Log.d("@dev", error.toString())
                    val message = when (error) {
                        is ErrorApp.InternetConexionError -> "Sin conexión a internet"
                        is ErrorApp.ServerErrorApp -> "Error en el servidor"
                        else -> "Error desconocido"
                    }
                    Toast.makeText(this@SimpsonsDetailActivity, "Error al cargar detalle: $message", Toast.LENGTH_SHORT).show()
                }

                uiState.simpson?.let { simpson ->
                    Log.d("@dev", "Personaje detalle: ${simpson.name}")
                    simpsonName.text = simpson.name
                    simpsonPhrase.text = simpson.phrase

                    simpsonImage.load(simpson.imageUrl) {
                        crossfade(true)
                    }
                }
            }
        }
        viewModel.uiState.observe(this, observer)
    }
}