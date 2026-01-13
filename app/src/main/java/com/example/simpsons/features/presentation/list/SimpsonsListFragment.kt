package com.example.simpsons.features.presentation.list

import SimpsonsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
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

    private val adapter by lazy {
        SimpsonsAdapter { simpson -> navigateToDetail(simpson.id) }
    }

    private var allSimpsons: List<Simpson> = emptyList()

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
        setUpRecyclerView()
        setUpSearchView()
        setUpBackPressHandler()
        setUpObserver()
        viewModel.loadSimpsons()
    }

    private fun setUpRecyclerView() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SimpsonsListFragment.adapter
        }
    }

    private fun setUpSearchView() {
        binding.searchView.setupWithSearchBar(binding.searchBar)

        binding.searchView.editText.addTextChangedListener { text ->
            filterList(text?.toString().orEmpty())
        }

        binding.searchView.editText.setOnEditorActionListener { _, _, _ ->
            val query = binding.searchView.text.toString()
            binding.searchBar.setText(query)
            binding.searchView.hide()
            filterList(query)
            false
        }
    }

    private fun setUpBackPressHandler() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.searchView.isShowing) {
                    binding.searchView.hide()
                } else {
                    isEnabled = false
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun filterList(query: String) {
        val filtered = if (query.isBlank()) {
            allSimpsons
        } else {
            allSimpsons.filter { it.name.contains(query, ignoreCase = true) }
        }
        adapter.submitList(filtered)
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
        binding.progressBar.visibility = View.VISIBLE
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
        allSimpsons = simpsons
        binding.progressBar.visibility = View.GONE
        binding.errorView.hide()
        binding.recycler.visibility = View.VISIBLE
        adapter.submitList(simpsons)
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