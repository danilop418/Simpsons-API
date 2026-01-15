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
            progressBar.visibility = View.GONE
            detailContent.visibility = View.VISIBLE
            
            simpsonName.text = simpson.name
            
            val imageLoader = { imageView: android.widget.ImageView ->
                imageView.load(simpson.imageUrl) {
                    crossfade(true)
                    placeholder(com.google.android.material.R.drawable.mtrl_ic_error)
                    error(com.google.android.material.R.drawable.mtrl_ic_error)
                }
            }
            imageLoader(headerAvatar)
            imageLoader(simpsonImage)

            chipGroup.removeAllViews()
            addChip(simpson.gender, chipGroup)
            addChip(simpson.status, chipGroup)
            if (simpson.occupation != "Unknown" && simpson.occupation.isNotEmpty()) {
                addChip(simpson.occupation, chipGroup)
            }

            simpsonDescription.text = if (simpson.description.isNotEmpty()) {
                simpson.description
            } else {
                "No description available."
            }

            simpsonPhrase.text = if (simpson.phrase.isNotEmpty()) {
                "\"${simpson.phrase}\""
            } else {
                "No phrase available."
            }

            buttonBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun addChip(text: String, chipGroup: com.google.android.material.chip.ChipGroup) {
        if (text.isEmpty() || text == "Unknown") return
        
        val chip = com.google.android.material.chip.Chip(requireContext()).apply {
            this.text = text
            isCheckable = false
            setEnsureMinTouchTargetSize(false)
            val typedValue = android.util.TypedValue()
            requireContext().theme.resolveAttribute(com.google.android.material.R.attr.colorSecondaryContainer, typedValue, true)
            setChipBackgroundColorResource(typedValue.resourceId.takeIf { it != 0 } ?: android.R.color.darker_gray)
            
            if (typedValue.resourceId == 0) {
                chipBackgroundColor = android.content.res.ColorStateList.valueOf(typedValue.data)
            }
        }
        chipGroup.addView(chip)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}