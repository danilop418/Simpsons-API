package com.example.simpsons.features.simpsons.presentation.initialization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.simpsons.databinding.FragmentInitializationBinding

class InitializationFragment : Fragment() {

    private var _binding: FragmentInitializationBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitializationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goToListButton.setOnClickListener {
            navigateToSimpsonsList()
        }
    }

    private fun navigateToSimpsonsList() {
        val directions =
            InitializationFragmentDirections.actionInitializationFragmentToSimpsonsListFragment()
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}