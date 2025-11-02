package com.example.simpsons.features.simpsons.presentation.detail

import SimpsonsDetailViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.simpsons.R
import com.example.simpsons.features.simpsons.core.api.ApiClient
import com.example.simpsons.features.simpsons.data.SimpsonsDataRepository
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.domain.ErrorApp
import com.example.simpsons.features.simpsons.domain.GetSimpsonByIdUseCase
import com.example.simpsons.features.simpsons.domain.Simpson
import coil.load

class SimpsonsDetailFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    private fun getSimpsonId(): String {
        return arguments?.getString("SIMPSON_ID") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        viewModel.loadSimpson(getSimpsonId())
    }

    private val viewModel = SimpsonsDetailViewModel(
        GetSimpsonByIdUseCase(
            SimpsonsDataRepository(
                SimpsonsApiRemoteDataSource(
                    ApiClient()
                )
            )
        )
    )


    private fun setUpObserver() {
        val observer = Observer<SimpsonsDetailViewModel.UiState> { uiState ->

            val progressBar: ProgressBar = requireView().findViewById(R.id.progressBar)
            val errorView: CardView = requireView().findViewById(R.id.errorView)

            if (uiState.isLoading) {
                progressBar.visibility = ProgressBar.VISIBLE
            } else {
                progressBar.visibility = ProgressBar.GONE
            }

            if (uiState.error != null) {
                val errorText = requireView().findViewById<TextView>(R.id.errorText)
                val retry = requireView().findViewById<Button>(R.id.retry)

                if (uiState.error == ErrorApp.ServerErrorApp) {
                    errorText.text = "Error del servidor \nIntentelo más tarde"
                } else if (uiState.error == ErrorApp.InternetConexionError) {
                    errorText.text = "Error de red \nRevise su conexión a internet"
                }

                errorView.visibility = CardView.VISIBLE
                retry.setOnClickListener {
                    viewModel.loadSimpson(getSimpsonId())
                }

            } else {
                errorView.visibility = CardView.GONE
            }

            uiState.simpson?.let { simpson ->
                showSimpsonDetail(simpson)
            }
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    private fun showSimpsonDetail(simpson: Simpson) {
        val imageView: ImageView = requireView().findViewById(R.id.simpsonImage)
        val nameView: TextView = requireView().findViewById(R.id.simpsonName)
        val phraseView: TextView = requireView().findViewById(R.id.simpsonPhrase)

        nameView.text = simpson.name
        phraseView.text = simpson.phrase
        imageView.load(simpson.imageUrl) {
            crossfade(true)
        }
    }
}