package com.example.simpleviralgamesassignment.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.simpleviralgamesassignment.R
import com.example.simpleviralgamesassignment.databinding.HomeScreenFragmentBinding

class HomeScreenFragment : Fragment(R.layout.home_screen_fragment) {
    private var _binding : HomeScreenFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = HomeScreenFragmentBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        _binding?.btnNavigateToGenerateRandomDogFragment?.setOnClickListener {
            val randomDogGeneratorFragment = RandomDogGeneratorFragment()
            navigate(randomDogGeneratorFragment)
        }

        _binding?.btnNavigateToRecentlyGeneratedDogs?.setOnClickListener {
            val cachedDogsFragment = CachedDogsFragment()
            navigate(cachedDogsFragment)
        }
    }

    private fun navigate(destinationFragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, destinationFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}