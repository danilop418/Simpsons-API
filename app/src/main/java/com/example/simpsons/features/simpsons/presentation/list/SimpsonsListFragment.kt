package com.example.simpsons.features.simpsons.presentation.list

import SimpsonsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpsons.R
import com.example.simpsons.features.simpsons.core.api.ApiClient
import com.example.simpsons.features.simpsons.data.SimpsonsDataRepository
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.domain.ErrorApp
import com.example.simpsons.features.simpsons.domain.FetchSimpsonsUseCase
import com.example.simpsons.features.simpsons.domain.Simpson

class SimpsonsListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        viewModel.loadSimpsons()
    }

    private val viewModel = SimpsonsListViewModel(
        FetchSimpsonsUseCase(
            SimpsonsDataRepository(
                SimpsonsApiRemoteDataSource(
                    ApiClient()
                )
            )
        )
    )

    private fun setUpObserver(){
        val observer = Observer<SimpsonsListViewModel.UiState> { uiState ->

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
                    viewModel.loadSimpsons()
                }

            } else {
                errorView.visibility = CardView.GONE
            }

            uiState.simpsons?.let {
                setUpRecyclerView(uiState.simpsons)
            }
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    private fun setUpRecyclerView(simpsonsList: List<Simpson>) {
        val adapter = SimpsonsAdapter(simpsonsList)
        val recyclerView: RecyclerView = requireView().findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}