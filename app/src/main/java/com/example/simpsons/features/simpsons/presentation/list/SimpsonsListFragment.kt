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
import com.example.simpsons.databinding.FragmentListBinding
import com.example.simpsons.features.simpsons.core.api.ApiClient
import com.example.simpsons.features.simpsons.data.SimpsonsDataRepository
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.domain.FetchSimpsonsUseCase
import com.example.simpsons.features.simpsons.domain.Simpson

class SimpsonsListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel = SimpsonsListViewModel(
        FetchSimpsonsUseCase(
            SimpsonsDataRepository(
                SimpsonsApiRemoteDataSource(
                    ApiClient()
                )
            )
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        viewModel.loadSimpsons()
    }

    private fun setUpObserver() {
        val observer = Observer<SimpsonsListViewModel.UiState> { uiState ->
            if (uiState.isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.recycler.visibility = View.GONE
                binding.errorView.visibility = View.GONE
            } else if (uiState.error != null) {
                binding.progressBar.visibility = View.GONE
                binding.recycler.visibility = View.GONE
                binding.errorView.visibility = View.VISIBLE
                binding.retry.setOnClickListener {
                    viewModel.loadSimpsons()
                }
            } else if (uiState.simpsons != null) {
                binding.progressBar.visibility = View.GONE
                binding.errorView.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
                setUpRecyclerView(uiState.simpsons)
            }
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    private fun setUpRecyclerView(simpsonsList: List<Simpson>) {
        val adapter = SimpsonsAdapter(simpsonsList) { simpson ->
            navigateToDetail(simpson.id)
        }
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun navigateToDetail(simpsonId: String) {
        val action = SimpsonsListFragmentDirections.actionSimpsonsListFragmentToSimpsonsDetailFragment(simpsonId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}