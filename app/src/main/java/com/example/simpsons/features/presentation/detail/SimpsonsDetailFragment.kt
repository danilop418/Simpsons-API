package com.example.simpsons.features.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.simpsons.databinding.FragmentDetailBinding
import com.example.simpsons.core.domain.ErrorApp
import com.example.simpsons.core.presentation.errors.ErrorAppFactory
import com.example.simpsons.features.domain.Simpson
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimpsonsDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: SimpsonsDetailFragmentArgs by navArgs()

    private val viewModel: SimpsonsDetailViewModel by viewModel()

    private val errorFactory by lazy { ErrorAppFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        viewModel.loadSimpson(args.simpsonId)
    }

    private fun setUpObserver() {
        val observer = Observer<SimpsonsDetailViewModel.UiState> { uiState ->
            when {
                uiState.isLoading -> showLoading()
                uiState.error != null -> showError(uiState.error)
                uiState.simpson != null -> showSimpsonDetail(uiState.simpson)
            }
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.detailContent.visibility = View.GONE
        binding.errorView.hide()
    }

    private fun showError(error: ErrorApp) {
        binding.progressBar.visibility = View.GONE
        binding.detailContent.visibility = View.GONE
        val errorUI = errorFactory.build(error) { viewModel.loadSimpson(args.simpsonId) }
        binding.errorView.render(errorUI)
    }

    private fun showSimpsonDetail(simpson: Simpson) {
        binding.apply {
            simpsonName.text = simpson.name
            simpsonPhrase.text = simpson.phrase
            simpsonImage.load(simpson.imageUrl) {
                crossfade(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}