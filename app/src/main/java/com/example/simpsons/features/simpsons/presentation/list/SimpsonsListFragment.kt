package com.example.simpsons.features.simpsons.presentation.list

import SimpsonsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpsons.R
import com.example.simpsons.databinding.FragmentListBinding
import com.example.simpsons.features.simpsons.core.api.ApiClient
import com.example.simpsons.features.simpsons.data.SimpsonsDataRepository
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.domain.ErrorApp
import com.example.simpsons.features.simpsons.domain.FetchSimpsonsUseCase
import com.example.simpsons.features.simpsons.domain.Simpson

class SimpsonsListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
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

            binding.progressBar.visibility = if (uiState.isLoading) {
                View.VISIBLE
            } else {
                View.GONE
            }

            if (uiState.error != null) {
                binding.errorText.text = when (uiState.error) {
                    ErrorApp.ServerErrorApp -> getString(R.string.error_server)
                    ErrorApp.InternetConexionError -> getString(R.string.error_network)
                }

                binding.errorView.visibility = View.VISIBLE
                binding.retry.setOnClickListener {
                    viewModel.loadSimpsons()
                }
            } else {
                binding.errorView.visibility = View.GONE
            }
            uiState.simpsons?.let {
                setUpRecyclerView(it)
            }
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    private fun setUpRecyclerView(simpsonsList: List<Simpson>) {
        val adapter = SimpsonsAdapter(simpsonsList) { simpson ->
            navigateToDetail(simpson.id)
        }
        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun navigateToDetail(simpsonId: String) {
        val action = SimpsonsListFragmentDirections.actionSimpsonsListFragmentToSimpsonsDetailFragment(simpsonId)
        findNavController().navigate(action)
    }
}