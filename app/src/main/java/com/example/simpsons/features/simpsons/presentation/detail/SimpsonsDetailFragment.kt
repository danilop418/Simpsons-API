package com.example.simpsons.features.simpsons.presentation.detail

import SimpsonsDetailViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.simpsons.databinding.FragmentDetailBinding
import com.example.simpsons.features.simpsons.core.api.ApiClient
import com.example.simpsons.features.simpsons.data.SimpsonsDataRepository
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.domain.GetSimpsonByIdUseCase
import com.example.simpsons.features.simpsons.domain.Simpson

class SimpsonsDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: SimpsonsDetailFragmentArgs by navArgs()

    private val viewModel = SimpsonsDetailViewModel(
        GetSimpsonByIdUseCase(
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
            if (uiState.isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.detailContent.visibility = View.GONE
                binding.errorView.visibility = View.GONE
            } else if (uiState.error != null) {
                binding.progressBar.visibility = View.GONE
                binding.detailContent.visibility = View.GONE
                binding.errorView.visibility = View.VISIBLE
                binding.retry.setOnClickListener {
                    viewModel.loadSimpson(args.simpsonId)
                }
            } else if (uiState.simpson != null) {
                binding.progressBar.visibility = View.GONE
                binding.errorView.visibility = View.GONE
                binding.detailContent.visibility = View.VISIBLE
                showSimpsonDetail(uiState.simpson)
            }
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
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