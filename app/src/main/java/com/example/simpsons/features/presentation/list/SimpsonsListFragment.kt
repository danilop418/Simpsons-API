package com.example.simpsons.features.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpsons.core.domain.ErrorApp
import com.example.simpsons.core.presentation.errors.ErrorAppFactory
import com.example.simpsons.databinding.FragmentListBinding
import com.example.simpsons.features.presentation.list.adapter.SimpsonsPagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimpsonsListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SimpsonsListViewModel by viewModel()

    private val errorFactory by lazy { ErrorAppFactory(requireContext()) }

    private val adapter by lazy {
        SimpsonsPagingAdapter(
            onItemClick = { simpson -> navigateToDetail(simpson.id) }
        )
    }

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
        // Search and Filter logic disabled during Paging 3 migration
        // setUpSearchView()
        // setUpFilterChip()
        // setUpBackPressHandler() // Can be restored if needed
        setUpPagingObservation()
    }

    private fun setUpRecyclerView() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SimpsonsListFragment.adapter
        }
    }

    private fun setUpPagingObservation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.simpsonsPagingData.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest { loadStates ->
                    // Initial Load
                    binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                    
                    // Error Handling
                    val errorState = loadStates.refresh as? LoadState.Error
                        ?: loadStates.append as? LoadState.Error
                        ?: loadStates.prepend as? LoadState.Error

                    errorState?.let {
                        val error = it.error
                        // Fallback error handling if not ErrorApp
                        // For fully correct error mapping, we'd need to map Throwable to ErrorApp here or in repo
                        showError(com.example.simpsons.core.domain.ErrorApp.ServerErrorApp) 
                    }
                    
                    if (loadStates.refresh is LoadState.NotLoading) {
                         binding.recycler.visibility = View.VISIBLE
                         binding.errorView.hide()
                    }
                }
            }
        }
    }

    private fun showError(error: ErrorApp) {
        binding.progressBar.visibility = View.GONE
        binding.recycler.visibility = View.GONE
        val errorUI = errorFactory.build(error) { adapter.retry() }
        binding.errorView.render(errorUI)
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