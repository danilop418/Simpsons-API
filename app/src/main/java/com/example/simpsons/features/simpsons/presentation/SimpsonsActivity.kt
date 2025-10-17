package com.example.simpsons.features.simpsons.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpsons.R
import com.example.simpsons.features.simpsons.data.SimpsonsDataRepository
import com.example.simpsons.features.simpsons.data.core.api.ApiClient
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.domain.FetchSimpsonsUseCase

class SimpsonsActivity : AppCompatActivity() {
    private lateinit var adapter: SimpsonAdapter
    private lateinit var viewModel: SimpsonsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_simpsons)

        setupRecyclerView()
        setupViewModel()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.simpsonRecyclerView)
        ViewCompat.setOnApplyWindowInsetsListener(recyclerView) { v, insets ->
            val innerPadding = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            v.setPadding(innerPadding.left, innerPadding.top, innerPadding.right, innerPadding.bottom)
            insets
        }

        adapter = SimpsonAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        val apiClient = ApiClient()
        val remoteDataSource = SimpsonsApiRemoteDataSource(apiClient)
        val repository = SimpsonsDataRepository(remoteDataSource)
        val fetchSimpsonsUseCase = FetchSimpsonsUseCase(repository)
        viewModel = SimpsonsListViewModel(fetchSimpsonsUseCase)
    }

    private fun observeViewModel() {
        viewModel.simpsons.observe(this) { simpsons ->
            adapter.updateList(simpsons)
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                val message = when (it) {
                    is com.example.simpsons.features.simpsons.domain.ErrorApp.InternetConexionError ->
                        "Sin conexión a Internet"
                    is com.example.simpsons.features.simpsons.domain.ErrorApp.ServerErrorApp ->
                        "Error del servidor"
                    else -> "Error desconocido"
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loadSimpsons()
    }
}