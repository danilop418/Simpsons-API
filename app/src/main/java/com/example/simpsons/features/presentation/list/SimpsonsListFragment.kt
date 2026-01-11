package com.example.simpsons.features.presentation.list

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
import com.example.simpsons.core.domain.ErrorApp
import com.example.simpsons.core.presentation.errors.ErrorAppFactory
import com.example.simpsons.features.domain.Simpson
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimpsonsListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SimpsonsListViewModel by viewModel()

    private val errorFactory by lazy { ErrorAppFactory(requireContext()) }

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
            when {
                uiState.isLoading -> showLoading()
                uiState.error != null -> showError(uiState.error)
                uiState.simpsons != null -> showSimpsons(uiState.simpsons)
            }
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    private fun showLoading() {
        binding.recycler.visibility = View.GONE
        binding.errorView.hide()
    }

    private fun showError(error: ErrorApp) {
        binding.progressBar.visibility = View.GONE
        binding.recycler.visibility = View.GONE
        val errorUI = errorFactory.build(error) { viewModel.loadSimpsons() }
        binding.errorView.render(errorUI)
    }

    private fun showSimpsons(simpsons: List<Simpson>) {
        binding.progressBar.visibility = View.GONE
        binding.errorView.hide()
        binding.recycler.visibility = View.VISIBLE
        setUpRecyclerView(simpsons)
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